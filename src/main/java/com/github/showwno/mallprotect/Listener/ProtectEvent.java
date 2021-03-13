package com.github.showwno.mallprotect.Listener;

import com.github.showwno.mallprotect.MallProtect;
import com.github.showwno.mallprotect.Util.ColorSearch;
import com.github.showwno.mallprotect.Util.LocationStructure;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.ArrayList;
import java.util.List;

public class ProtectEvent implements Listener {

    //前もってストラクチャ―を作成しておき、sortを使わず処理する。
    //X -> Z -> Y　の順で処理することで高速化
    //Mall以外の処理ができるように一応Listにしておくが速度を上げるにはリストはないほうが良い。

    //Kyori adventure を使用していきたい。

    private final List<LocationStructure> locationStructureList;


    public ProtectEvent(MallProtect plugin){
        this.locationStructureList = plugin.getMallConfig().getLocationStructureList();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.getPlayer().hasPermission("op")) {
            if(!e.getBlock().getType().equals(Material.SHULKER_BOX)){
                for(LocationStructure ls : locationStructureList){
                    if(ls.checkLocation(e.getBlock().getLocation())){
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(Component.text("ここでブロック破壊は出来ません!").color(ColorSearch.Red));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!e.getPlayer().hasPermission("op")){
            if(!e.getBlock().getType().equals(Material.SHULKER_BOX)){
                for(LocationStructure ls : locationStructureList){
                    if(ls.checkLocation(e.getBlock().getLocation())){
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(Component.text("ここでブロック設置は出来ません!").color(ColorSearch.Red));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (e.getEntity().getType().equals(EntityType.PLAYER)) return;
        if (!e.getEntity().getType().isAlive()) return;
        for(LocationStructure ls : locationStructureList){
            if(ls.checkLocation(e.getEntity().getLocation())){
                e.setCancelled(true);
            }
        }
    }

    //一か所でもかぶっていれば爆破をキャンセルすることで高速化
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        boolean isInMall = false;
        for (Block b: e.blockList()) {
            for(LocationStructure ls : locationStructureList){
                if(ls.checkLocation(b.getLocation())){
                    isInMall = true;
                    break;
                }
            }
        }
        if(isInMall){
            e.setCancelled(true);
        }
    }

    //一か所でもかぶっていれば爆破をキャンセルすることで高速化
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        boolean isInMall = false;
        for (Block b: e.blockList()) {
            for(LocationStructure ls : locationStructureList){
                if(ls.checkLocation(b.getLocation())){
                    isInMall = true;
                    break;
                }
            }
        }
        if(isInMall){
            e.setCancelled(true);
        }
    }
}
