package me.symi.owndrop.gui;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
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

public class DropGUI {

    public static Inventory getDropOptionsInventory(Player player)
    {
        DropSettings dropSettings = Main.getInstance().getPlayerDataManager().getDropSettings(player);
        Inventory inv = Bukkit.createInventory(null, Main.getInstance().getConfigManager().getDrop_settings_inventory_size(), ChatUtil.fixColors("&e&lDrop &8» &4Opcje"));

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
        Inventory inv = Bukkit.createInventory(null, 27, ChatUtil.fixColors("&e&lDrop &8» &4Ustawienia"));
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        for(int i = 0; i < inv.getSize(); i++)
        {
            inv.setItem(i, empty);
        }

        ItemStack drop_cobble = new ItemStack(Material.COBBLESTONE);
        ItemMeta drop_cobble_meta = drop_cobble.getItemMeta();
        drop_cobble_meta.setDisplayName(ChatUtil.fixColors("&7Drop Cobblestone"));
        drop_cobble_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (dropSettings.isCobblestone_drop() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())
        )));
        drop_cobble.setItemMeta(drop_cobble_meta);

        ItemStack drop_exp = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta drop_exp_meta = drop_exp.getItemMeta();
        drop_exp_meta.setDisplayName(ChatUtil.fixColors("&7Drop EXPa"));
        drop_exp_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (dropSettings.isExp_drop() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())
        )));
        drop_exp.setItemMeta(drop_exp_meta);

        ItemStack drop_messages = new ItemStack(Material.PAPER);
        ItemMeta drop_messages_meta = drop_messages.getItemMeta();
        drop_messages_meta.setDisplayName(ChatUtil.fixColors("&7Wiadomosci"));
        drop_messages_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (dropSettings.isMessages() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())
        )));
        drop_messages.setItemMeta(drop_messages_meta);

        ItemStack drop_sound = new ItemStack(Material.NOTE_BLOCK);
        ItemMeta drop_sound_meta = drop_sound.getItemMeta();
        drop_sound_meta.setDisplayName(ChatUtil.fixColors("&7Dzwieki"));
        drop_sound_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (dropSettings.isSounds() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())
        )));
        drop_sound.setItemMeta(drop_sound_meta);



        inv.setItem(10, drop_sound);
        inv.setItem(12, drop_messages);
        inv.setItem(14, drop_exp);
        inv.setItem(16, drop_cobble);

        return inv;
    }

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
                "&7Status: " + (Main.getInstance().isTurbo_drop() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())
        )));
        turbo.setItemMeta(turbo_meta);

        inv.setItem(11, drop);
        inv.setItem(13, settings);
        inv.setItem(15, turbo);

        return inv;
    }

}
