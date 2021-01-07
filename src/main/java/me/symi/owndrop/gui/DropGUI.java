package me.symi.owndrop.gui;

import me.symi.owndrop.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DropGUI {

    public static Inventory getMainInventory()
    {
        Inventory inv = Bukkit.createInventory(null, 27, ChatUtil.fixColors("&e&lDrop &8» &4Menu"));
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        for(int i = 0; i < inv.getSize(); i++)
        {
            inv.setItem(i, empty);
        }
        ItemStack drop = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta drop_meta = drop.getItemMeta();
        drop_meta.setDisplayName(ChatUtil.fixColors("&7Opcje &eDropu"));
        drop_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&eKliknij, aby zobaczyc opcje dropu ze stone."
        )));
        drop.setItemMeta(drop_meta);

        ItemStack settings = new ItemStack(Material.BEACON);
        ItemMeta settings_meta = settings.getItemMeta();
        settings_meta.setDisplayName(ChatUtil.fixColors("&7Ustawienia &eDropu"));
        settings_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&eKliknij, aby zobaczyc ustawienia dropu ze stone."
        )));
        settings.setItemMeta(settings_meta);

        ItemStack turbo = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta turbo_meta = turbo.getItemMeta();
        turbo_meta.setDisplayName(ChatUtil.fixColors("&7Turbo &cDrop"));
        turbo_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: &a✔️"
        )));
        turbo.setItemMeta(turbo_meta);

        inv.setItem(11, drop);
        inv.setItem(13, settings);
        inv.setItem(15, turbo);

        return inv;
    }

}
