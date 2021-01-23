package me.symi.owndrop.listeners;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
import me.symi.owndrop.gui.DropGUI;
import me.symi.owndrop.manager.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event)
    {
        if(event.getClickedInventory() == null)
        {
            return;
        }

        if(event.getView().getTitle().equalsIgnoreCase(Main.getInstance().getConfigManager().getDrop_menu_gui_title()))
        {
            event.setCancelled(true);
            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            if(event.getCurrentItem() == null || event.getCurrentItem().hasItemMeta() == false
                || event.getCurrentItem().getItemMeta().getDisplayName() == null)
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();
            ConfigManager manager = Main.getInstance().getConfigManager();

            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(manager.getDrop_settings_item().getItemMeta().getDisplayName()))
            {
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(manager.getDrop_options_item().getItemMeta().getDisplayName()))
            {
                player.openInventory(DropGUI.getDropOptionsInventory(player));
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(Main.getInstance().getConfigManager().getDrop_settings_gui_title()))
        {
            event.setCancelled(true);
            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            if(event.getCurrentItem() == null || event.getCurrentItem().hasItemMeta() == false
                    || event.getCurrentItem().getItemMeta().getDisplayName() == null)
            {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            DropSettings dropSettings = Main.getInstance().getPlayerDataManager().getDropSettings(player);
            ConfigManager manager = Main.getInstance().getConfigManager();

            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(manager.getCobblestone_drop_item().getItemMeta().getDisplayName()))
            {
                dropSettings.setCobblestone_drop(!dropSettings.isCobblestone_drop());
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(manager.getExp_drop_item().getItemMeta().getDisplayName()))
            {
                dropSettings.setExp_drop(!dropSettings.isExp_drop());
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(manager.getMessages_item().getItemMeta().getDisplayName()))
            {
                dropSettings.setMessages(!dropSettings.isMessages());
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(manager.getSounds_item().getItemMeta().getDisplayName()))
            {
                dropSettings.setSounds(!dropSettings.isSounds());
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(Main.getInstance().getConfigManager().getDrop_options_gui_title()))
        {
            event.setCancelled(true);
            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            if(event.getCurrentItem() == null || event.getCurrentItem().hasItemMeta() == false
                    || event.getCurrentItem().getItemMeta().getDisplayName() == null)
            {
                return;
            }

            ItemStack item = event.getCurrentItem();
            final Player player = (Player) event.getWhoClicked();
            final DropSettings dropSettings = Main.getInstance().getPlayerDataManager().getDropSettings(player);
            final ConfigManager manager = Main.getInstance().getConfigManager();

            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(manager.getEnable_all_item().getItemMeta().getDisplayName()))
            {
                for(ItemStack items : event.getClickedInventory().getContents())
                {
                    if(items != null && !items.getType().toString().contains("WOOL"))
                    {
                        dropSettings.removeDisabledDropItem(items);
                    }
                }
                player.openInventory(DropGUI.getDropOptionsInventory(player));
                player.playSound(player.getLocation(), Main.getInstance().getConfigManager().getDrop_sound(), 1.0f, 1.0f);
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(manager.getDisable_all_item().getItemMeta().getDisplayName()))
            {
                for(ItemStack items : event.getClickedInventory().getContents())
                {
                    if(items != null && !items.getType().toString().contains("WOOL"))
                    {
                        dropSettings.disableDropItem(items);
                    }
                }
                player.openInventory(DropGUI.getDropOptionsInventory(player));
                player.playSound(player.getLocation(), Main.getInstance().getConfigManager().getDrop_sound(), 1.0f, 1.0f);
            }
            else if(item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size() >= 3)
            {
                boolean is_enabled = true;
                String cross_mark = Main.getInstance().getConfigManager().getCross_mark();
                for(String text : item.getItemMeta().getLore())
                {
                    if(text.contains(cross_mark))
                    {
                        is_enabled = false;
                        break;
                    }
                }

                if(is_enabled)
                {
                    dropSettings.disableDropItem(item);
                }
                else
                {
                    dropSettings.removeDisabledDropItem(item);
                }
                player.openInventory(DropGUI.getDropOptionsInventory(player));
            }
        }
    }

}
