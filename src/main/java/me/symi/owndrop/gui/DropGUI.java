package me.symi.owndrop.gui;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
import me.symi.owndrop.manager.ConfigManager;
import me.symi.owndrop.owndrop.DropItem;
import me.symi.owndrop.utils.ChatUtil;
import me.symi.owndrop.utils.StatusUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DropGUI {

    public static Inventory getDropOptionsInventory(Player player)
    {
        DropSettings dropSettings = Main.getInstance().getPlayerDataManager().getDropSettings(player);
        Inventory inv = Bukkit.createInventory(null, Main.getInstance().getConfigManager().getDrop_settings_inventory_size(), Main.getInstance().getConfigManager().getDrop_options_gui_title());

        int counter = 0;

        ArrayList<DropItem> sorted_items = Main.getInstance().getDropManager().getSorted_items();
        for(DropItem dropItem : sorted_items)
        {
            double chance = dropItem.getChance();
            double extra_chance = 1.0;
            HashMap<String, Double> multipilers = Main.getInstance().getConfigManager().getMultipilers();
            for(String permission : multipilers.keySet())
            {
                double multipiler = multipilers.get(permission);
                if(player.hasPermission(permission) && extra_chance < multipiler)
                {
                    extra_chance = multipiler;
                }
            }

            chance = chance * extra_chance;

            if(Main.getInstance().isTurbo_drop())
            {
                chance = chance * 2;
            }

            String strChance = String.format("%.2f", chance);
            ItemStack gui_item = new ItemStack(dropItem.getMaterial());
            ItemMeta gui_item_meta = gui_item.getItemMeta();
            gui_item_meta.setDisplayName(dropItem.getItemName());

            ItemStack item = dropItem.parseItem();
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(dropItem.getItemName());
            item.setItemMeta(itemMeta);

            gui_item_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7Szansa: &e" + strChance + "%",
                    "&7Ilosc: &ex" + dropItem.getAmount(),
                    "&7Status: " + (dropSettings.isDropDisabled(item) ? StatusUtil.getCrossMark() : StatusUtil.getCheckMark())
            )));
            gui_item.setItemMeta(gui_item_meta);

            if(Main.getInstance().isTurbo_drop())
            {
                gui_item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            }

            inv.setItem(counter, gui_item);
            counter++;
        }

        ItemStack enable_all = new ItemStack(Material.LIME_WOOL);
        ItemMeta enable_all_meta = enable_all.getItemMeta();
        enable_all_meta.setDisplayName(ChatUtil.fixColors("&aWlacz wszystko"));
        enable_all_meta.setLore(ChatUtil.fixColors(Arrays.asList("&7Kliknij, aby &awlaczyc&7 wszystkie dropy")));
        enable_all.setItemMeta(enable_all_meta);

        ItemStack disable_all = new ItemStack(Material.RED_WOOL);
        ItemMeta disable_all_meta = disable_all.getItemMeta();
        disable_all_meta.setDisplayName(ChatUtil.fixColors("&cWylacz wszystko"));
        disable_all_meta.setLore(ChatUtil.fixColors(Arrays.asList("&7Kliknij, aby &cwylaczyc&7 wszystkie dropy")));
        disable_all.setItemMeta(disable_all_meta);

        inv.setItem(inv.getSize() - 9, enable_all);
        inv.setItem(inv.getSize() - 8, disable_all);

        return inv;
    }

    public static Inventory getDropSettingsInventory(Player player)
    {
        DropSettings dropSettings = Main.getInstance().getPlayerDataManager().getDropSettings(player);
        ConfigManager manager = Main.getInstance().getConfigManager();

        Inventory inv = Bukkit.createInventory(null, 27, Main.getInstance().getConfigManager().getDrop_settings_gui_title());
        ItemStack empty = new ItemStack(manager.getDrop_settings_gui_fill_item_material());

        for(int i = 0; i < inv.getSize(); i++)
        {
            inv.setItem(i, empty);
        }

        ItemStack drop_sound = manager.getSounds_item().clone();
        ItemMeta drop_sound_meta = drop_sound.getItemMeta();
        List<String> drop_sound_lore = drop_sound_meta.getLore();
        for(int i = 0; i < drop_sound_lore.size(); i++)
        {
            drop_sound_lore.set(i, drop_sound_lore.get(i).replace("%status%",
                    (dropSettings.isSounds() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())));
        }
        drop_sound_meta.setLore(drop_sound_lore);
        drop_sound.setItemMeta(drop_sound_meta);

        ItemStack drop_messages = manager.getMessages_item().clone();
        ItemMeta drop_messages_meta = drop_messages.getItemMeta();
        List<String> drop_messages_lore = drop_messages_meta.getLore();
        for(int i = 0; i < drop_messages_lore.size(); i++)
        {
            drop_messages_lore.set(i, drop_messages_lore.get(i).replace("%status%",
                    (dropSettings.isMessages() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())));
        }
        drop_messages_meta.setLore(drop_messages_lore);
        drop_messages.setItemMeta(drop_messages_meta);

        ItemStack drop_exp = manager.getExp_drop_item().clone();
        ItemMeta drop_exp_meta = drop_exp.getItemMeta();
        List<String> drop_exp_lore = drop_exp_meta.getLore();
        for(int i = 0; i < drop_exp_lore.size(); i++)
        {
            drop_exp_lore.set(i, drop_exp_lore.get(i).replace("%status%",
                    (dropSettings.isExp_drop() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())));
        }
        drop_exp_meta.setLore(drop_exp_lore);
        drop_exp.setItemMeta(drop_exp_meta);

        ItemStack drop_cobble = manager.getCobblestone_drop_item().clone();
        ItemMeta drop_cobble_meta = drop_cobble.getItemMeta();
        List<String> drop_cobble_lore = drop_cobble_meta.getLore();
        for(int i = 0; i < drop_cobble_lore.size(); i++)
        {
            drop_cobble_lore.set(i, drop_cobble_lore.get(i).replace("%status%",
                    (dropSettings.isCobblestone_drop() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())));
        }
        drop_cobble_meta.setLore(drop_cobble_lore);
        drop_cobble.setItemMeta(drop_cobble_meta);


        inv.setItem(10, drop_sound);
        inv.setItem(12, drop_messages);
        inv.setItem(14, drop_exp);
        inv.setItem(16, drop_cobble);

        return inv;
    }

    public static Inventory getMainInventory()
    {
        ConfigManager manager = Main.getInstance().getConfigManager();
        Inventory inv = Bukkit.createInventory(null, 27, Main.getInstance().getConfigManager().getDrop_menu_gui_title());
        ItemStack empty = new ItemStack(manager.getDrop_menu_gui_fill_item_material());

        for(int i = 0; i < inv.getSize(); i++)
        {
            inv.setItem(i, empty);
        }

        ItemStack drop = manager.getDrop_options_item();
        ItemStack settings = manager.getDrop_settings_item();

        ItemStack turbo = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta turbo_meta = turbo.getItemMeta();
        turbo_meta.setDisplayName(ChatUtil.fixColors("&7Turbo &cDrop"));
        turbo_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (Main.getInstance().isTurbo_drop() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())
        )));
        turbo.setItemMeta(turbo_meta);

        inv.setItem(11, drop);
        inv.setItem(13, settings);
        inv.setItem(15, turbo);

        return inv;
    }

}
