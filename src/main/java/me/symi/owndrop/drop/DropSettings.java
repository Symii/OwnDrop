package me.symi.owndrop.drop;

import me.symi.owndrop.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DropSettings {

    private Player player;
    private boolean cobblestone_drop, exp_drop, sounds, messages;
    private List<ItemStack> disabled_drop_items;

    public DropSettings(Player player, String data)
    {
        this.player = player;
        loadSettingsFromString(data);
        this.disabled_drop_items = new ArrayList<>();
    }

    public String settingsToString()
    {
        return cobblestone_drop + ";" + exp_drop + ";" + sounds + ";" + messages + ";";
    }

    public void loadSettingsFromString(String data)
    {
        String[] array = data.split(";");
        cobblestone_drop = Boolean.parseBoolean(array[0]);
        exp_drop = Boolean.parseBoolean(array[1]);
        sounds = Boolean.parseBoolean(array[2]);
        messages = Boolean.parseBoolean(array[3]);
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
        {
            disabled_drop_items.add(item);
        }
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
        saveAsync();
    }

    public void setExp_drop(boolean exp_drop) {
        this.exp_drop = exp_drop;
        saveAsync();
    }

    public void setSounds(boolean sounds) {
        this.sounds = sounds;
        saveAsync();
    }

    public void setMessages(boolean messages) {
        this.messages = messages;
        saveAsync();
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


    public void save()
    {
        try
        {
            String data = settingsToString();
            String query = "UPDATE players SET data=? WHERE playername=?";
            PreparedStatement statement = Main.getInstance().getDatabase().getConnection().prepareStatement(query);
            statement.setString(1, data);
            statement.setString(2, player.getName());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void saveAsync()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String data = settingsToString();
                    String query = "UPDATE players SET data=? WHERE playername=?";
                    PreparedStatement statement = Main.getInstance().getDatabase().getConnection().prepareStatement(query);
                    statement.setString(1, data);
                    statement.setString(2, player.getName());
                    statement.executeUpdate();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public void createPlayer()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String data = settingsToString();
                    PreparedStatement insert = Main.getInstance().getDatabase().getConnection().prepareStatement
                            ("INSERT INTO players (id,playername,data) VALUES(NULL,?,?);");
                    insert.setString(1, player.getName());
                    insert.setString(2, data);
                    insert.executeUpdate();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

}
