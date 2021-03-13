package com.github.showwno.mallprotect.Command;

import com.github.showwno.mallprotect.Listener.PrepareEvent;
import com.github.showwno.mallprotect.Listener.ProtectEvent;
import com.github.showwno.mallprotect.MallProtect;
import com.github.showwno.mallprotect.Util.ColorSearch;
import com.github.showwno.mallprotect.Util.LocationStructure;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MallProtectCommand implements CommandExecutor, TabCompleter {
    private final MallProtect plugin;
    private PrepareEvent listener;

    public MallProtectCommand(MallProtect plugin) {
        this.plugin = plugin;
        plugin.getCommand("protect").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You cannot use commands with the console.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length > 0) {
            if (player.hasPermission("protect")) {
                switch (args[0].toLowerCase()) {
                    case "select":
                        listener = new PrepareEvent(plugin, player);
                        player.sendMessage(Component.text("選択したら、/selected <名前> で登録されます。"));
                        break;
                    case "registe":
                        if(args.length == 2){
                            plugin.getMallConfig().getLocationStructureList().add(listener.getLocationStructure(args[1]));
                            player.sendMessage(Component.text("登録しました。"));
                            listener.deInit();
                            listener = null;
                            plugin.reloadPlugin();
                            player.sendMessage(Component.text("reloadしました。"));
                        } else {
                            player.sendMessage(Component.text("引数が違います。/selected <名前> で登録。"));
                        }
                        break;
                    case "remove":
                        if(args.length == 2){
                            boolean exist = false;
                            for(LocationStructure ls : plugin.getMallConfig().getLocationStructureList()){
                                if(ls.getName().equals(args[1])){
                                    plugin.getMallConfig().getLocationStructureList().remove(ls);
                                    exist = true;
                                    break;
                                }
                            }
                            if(exist){
                                player.sendMessage(Component.text(args[1]+"を削除しました。"));
                            } else {
                                player.sendMessage(Component.text(args[1]+"は存在しませんでした。"));
                            }
                            plugin.reloadPlugin();
                            player.sendMessage(Component.text("reloadしました。"));
                            break;
                        } else {
                            player.sendMessage(Component.text("引数が違います。/selected <名前> で登録。"));
                        }
                        break;
                    case "info":
                        plugin.getMallConfig().getLocationStructureList()
                                .forEach(l -> player.sendMessage(Component.text(l.locationString()).color(ColorSearch.Aqua)));
                        break;
                }
            } else {
                player.sendMessage("権限がありません。");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String command, String[] args) {
        List<String> autoComplete = new ArrayList<>();
        if (sender.hasPermission("protect")) {
            if (args.length == 1) {//一段目
                autoComplete.addAll(Arrays.asList("select", "registe", "info","remove"));
            } else if(args.length == 2){
                if(args[0].equals("registe")){
                    plugin.getMallConfig().getLocationStructureList()
                            .forEach(l -> autoComplete.add(l.getName()));
                }
            }
        }
        //文字比較と削除-----------------------------------------------------
        //Collections.sort(autoComplete);
        autoComplete.removeIf(str -> !str.startsWith(args[args.length - 1]));
        //------------------------------------------------------
        return autoComplete;
    }

    private int stringToInt(String str) {
        int x = -1;
        try {
            x = Integer.parseInt(str);
        } catch (Exception ignored) {
        }
        return x;
    }
}