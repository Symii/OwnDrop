package me.symi.owndrop.gui;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
import me.symi.owndrop.manager.ConfigManager;
import me.symi.owndrop.owndrop.DropItem;
import me.symi.owndrop.utils.StatusUtil;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DropGUI {

    public static Inventory getDropOptionsInventory(Player player)
    {
        DropSettings dropSettings = Main.getInstance().getPlayerDataManager().getDropSettings(player);
        Inventory inv = Bukkit.createInventory(null, Main.getInstance().getConfigManager().getDrop_settings_inventory_size(), Main.getInstance().getConfigManager().getDrop_options_gui_title());
        ConfigManager manager = Main.getInstance().getConfigManager();

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

            String strChance = "";
            if(chance >= 0.01)
            {
                strChance = String.format("%.2f", chance);
            }
            else
            {
                strChance = String.format("%.3f", chance);
            }

            ItemStack gui_item = new ItemStack(dropItem.getMaterial());
            ItemMeta gui_item_meta = gui_item.getItemMeta();
            gui_item_meta.setDisplayName(dropItem.getItemName());

            ItemStack item = dropItem.parseItem(player);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(dropItem.getItemName());
            item.setItemMeta(itemMeta);
            item.setAmount(1);

            List<String> gui_item_lore = new ArrayList<>(Main.getInstance().getConfigManager().getDrop_item_lore());
            for(int i = 0; i < gui_item_lore.size(); i++)
            {
                String text = gui_item_lore.get(i);
                text = text.replaceFirst("%status%", (dropSettings.isDropDisabled(item) ? StatusUtil.getCrossMark() : StatusUtil.getCheckMark()));
                text = text.replaceFirst("%chance%", strChance);

                text = text.replaceFirst("%fortune0%", dropItem.getFortune0min() + "-" + dropItem.getFortune0max());
                text = text.replaceFirst("%fortune1%", dropItem.getFortune1min() + "-" + dropItem.getFortune1max());
                text = text.replaceFirst("%fortune2%", dropItem.getFortune2min() + "-" + dropItem.getFortune2max());
                text = text.replaceFirst("%fortune3%", dropItem.getFortune3min() + "-" + dropItem.getFortune3max());

                gui_item_lore.set(i, text);
            }

            gui_item_meta.setLore(gui_item_lore);
            gui_item.setItemMeta(gui_item_meta);

            if(Main.getInstance().isTurbo_drop())
            {
                gui_item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            }

            inv.setItem(counter, gui_item);
            counter++;
        }

        ItemStack enable_all = manager.getEnable_all_item();
        ItemStack disable_all = manager.getDisable_all_item();

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

        ItemStack turbo = manager.getTurbo_drop_item().clone();
        ItemMeta turbo_meta = turbo.getItemMeta();
        List<String> turbo_lore = new ArrayList<>(turbo_meta.getLore());
        for(int i = 0; i < turbo_lore.size(); i++)
        {
            turbo_lore.set(i, turbo_lore.get(i).replace("%status%",
                    (Main.getInstance().isTurbo_drop() ? StatusUtil.getCheckMark() : StatusUtil.getCrossMark())));
        }
        turbo_meta.setLore(turbo_lore);
        turbo.setItemMeta(turbo_meta);

        inv.setItem(11, drop);
        inv.setItem(13, settings);
        inv.setItem(15, turbo);

        return inv;
    }

}
