package me.symi.owndrop.drop;

import org.bukkit.entity.Player;

public class DropSettings {

    private Player player;
    private boolean cobblestone_drop, exp_drop, sounds, messages;

    public DropSettings(Player player, boolean cobblestone_drop, boolean exp_drop, boolean sounds, boolean messages)
    {
        this.player = player;
        this.cobblestone_drop = cobblestone_drop;
        this.exp_drop = exp_drop;
        this.sounds = sounds;
        this.messages = messages;
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
