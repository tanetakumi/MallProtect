package com.github.showwno.mallprotect.Command;

import com.github.showwno.mallprotect.Listener.PrepareEvent;
import com.github.showwno.mallprotect.Listener.ProtectEvent;
import com.github.showwno.mallprotect.MallProtect;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                    case "selector":
                        listener = new PrepareEvent(plugin, player);
                        break;
                    case "off":
                        HandlerList.unregisterAll(listener);
                        listener = null;
                        player.sendMessage(Component.text("offにしました。"));
                        break;
                    case "reload":
                        plugin.reloadPlugin();
                        player.sendMessage(Component.text("reloadしました。"));
                        break;
                    case "info":
                        player.sendMessage(Component.text(plugin.getMallConfig().getLocationStructureList().toString()));
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
                autoComplete.addAll(Arrays.asList("selector", "off", "help", "reload"));
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