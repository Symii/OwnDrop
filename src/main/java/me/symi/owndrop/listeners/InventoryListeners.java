package me.symi.owndrop.listeners;

import me.symi.owndrop.utils.ChatUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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
        }
    }

}
