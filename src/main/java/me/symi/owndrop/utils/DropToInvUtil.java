package me.symi.owndrop.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropToInvUtil {

    public static void dropItem(Player player, ItemStack item, Location loc)
    {
        if(hasFullInventory(player, item) == false)
        {
            player.getInventory().addItem(item);
        }
        else
        {
            loc.add(0, 0.4, 0);
            player.getWorld().dropItem(loc, item);
            String message = ChatUtil.fixColors("§c» §7Brak miejsca w ekwipunku §c«");
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
    }

    public static boolean hasFullInventory(Player p, ItemStack itemStack)
    {
        ItemStack item = null;

        for(int i = 0; i <= 35; i++)
        {
            item = p.getInventory().getItem(i);
            if(item == null)
            {
                return false;
            }
            else
            {
                int amount = item.getAmount() + itemStack.getAmount();
                if(item.getType() == itemStack.getType() && amount <= 64)
                {
                    return false;
                }
            }
        }

        return true;
    }

}
