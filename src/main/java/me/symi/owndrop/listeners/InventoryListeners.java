package me.symi.owndrop.listeners;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
import me.symi.owndrop.gui.DropGUI;
import me.symi.owndrop.utils.ChatUtil;
import org.bukkit.Bukkit;
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

        if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColors("&e&lDrop &8» &4Menu")))
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
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&7Ustawienia &eDropu")))
            {
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&7Opcje &eDropu")))
            {
                player.openInventory(DropGUI.getDropOptionsInventory(player));
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColors("&e&lDrop &8» &4Ustawienia")))
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

            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&7Drop Cobblestone")))
            {
                dropSettings.setCobblestone_drop(!dropSettings.isCobblestone_drop());
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&7Drop EXPa")))
            {
                dropSettings.setExp_drop(!dropSettings.isExp_drop());
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&7Wiadomosci")))
            {
                dropSettings.setMessages(!dropSettings.isMessages());
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&7Dzwieki")))
            {
                dropSettings.setSounds(!dropSettings.isSounds());
                player.openInventory(DropGUI.getDropSettingsInventory(player));
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColors("&e&lDrop &8» &4Opcje")))
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

            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aWlacz wszystko")))
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
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cWylacz wszystko")))
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
                if(item.getItemMeta().getLore().get(2).contains("§c"))
                {
                    dropSettings.removeDisabledDropItem(item);
                }
                else if(item.getItemMeta().getLore().get(2).contains("§a"))
                {
                    dropSettings.disableDropItem(item);
                }
                player.openInventory(DropGUI.getDropOptionsInventory(player));
            }
        }
    }

}
