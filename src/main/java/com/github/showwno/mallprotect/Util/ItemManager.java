package com.github.showwno.mallprotect.Util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

    public static void setPrepareStick(Player player, int slot, String displayName){

        ItemStack item = new ItemStack(Material.STICK,1);
        ItemMeta im = item.getItemMeta();
        im.displayName(Component.text(displayName).color(ColorSearch.Gold));
        item.setItemMeta(im);
        player.getInventory().setItem(slot,item);
    }
}
