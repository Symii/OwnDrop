package me.symi.owndrop.drop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DropSettings {

    private Player player;
    private boolean cobblestone_drop, exp_drop, sounds, messages;
    private List<ItemStack> disabled_drop_items;

    public DropSettings(Player player, boolean cobblestone_drop, boolean exp_drop, boolean sounds, boolean messages)
    {
        this.player = player;
        this.cobblestone_drop = cobblestone_drop;
        this.exp_drop = exp_drop;
        this.sounds = sounds;
        this.messages = messages;
        this.disabled_drop_items = new ArrayList<>();
    }

    public List<ItemStack> getDisabled_drop_items() {
        return disabled_drop_items;
    }

    public void removeDisabledDropItem(ItemStack item)
    {
        for(ItemStack i : disabled_drop_items)
        {
            if(i.getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemMeta().getDisplayName()))
            {
                disabled_drop_items.remove(i);
                break;
            }
        }
    }

    public void disableDropItem(ItemStack item)
    {
        if(disabled_drop_items.contains(item) == false)
            disabled_drop_items.add(item);
    }

    public boolean isDropDisabled(ItemStack item)
    {
        for(ItemStack i : disabled_drop_items)
        {
            if(item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(i.getItemMeta().getDisplayName()))
            {
                return true;
            }
        }
        return false;
    }

    public void setCobblestone_drop(boolean cobblestone_drop) {
        this.cobblestone_drop = cobblestone_drop;
    }

    public void setExp_drop(boolean exp_drop) {
        this.exp_drop = exp_drop;
    }

    public void setSounds(boolean sounds) {
        this.sounds = sounds;
    }

    public void setMessages(boolean messages) {
        this.messages = messages;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCobblestone_drop() {
        return cobblestone_drop;
    }

    public boolean isExp_drop() {
        return exp_drop;
    }

    public boolean isSounds() {
        return sounds;
    }

    public boolean isMessages() {
        return messages;
    }
}
