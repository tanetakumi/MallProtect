package com.github.showwno.mallprotect.Listener;

import com.github.showwno.mallprotect.MallProtect;
import com.github.showwno.mallprotect.Util.ColorSearch;
import com.github.showwno.mallprotect.Util.ItemManager;
import com.github.showwno.mallprotect.Util.LocationStructure;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PrepareEvent implements Listener {

    //前もってストラクチャ―を作成しておき、sortを使わず処理する。
    //X -> Z -> Y　の順で処理することで高速化
    //Mall以外の処理ができるように一応Listにしておくが速度を上げるにはリストはないほうが良い。

    //Kyori adventure を使用していきたい。
    private final MallProtect plugin;
    private final Player targetPlayer;

    public PrepareEvent(MallProtect plugin, Player player){
        this.plugin = plugin;
        this.targetPlayer = player;
        ItemManager.setPrepareStick(player,0,"Location1");
        ItemManager.setPrepareStick(player,1,"Location2");
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(player.getName().equals(targetPlayer.getName())){
            ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if(itemMeta!=null && itemMeta.hasDisplayName()){
                if(PlainComponentSerializer.plain().serialize(itemMeta.displayName()).equals("Location1")){
                    e.setCancelled(true);
                    plugin.getMallConfig().saveLocationsData("loc1",e.getBlock().getLocation());
                    player.sendMessage(Component.text("Location1をセットしました").color(ColorSearch.Gold));
                } else if(PlainComponentSerializer.plain().serialize(itemMeta.displayName()).equals("Location2")){
                    plugin.getMallConfig().saveLocationsData("loc2",e.getBlock().getLocation());
                    e.setCancelled(true);
                    player.sendMessage(Component.text("Location2をセットしました").color(ColorSearch.Gold));
                }
            }
        }
    }

}
