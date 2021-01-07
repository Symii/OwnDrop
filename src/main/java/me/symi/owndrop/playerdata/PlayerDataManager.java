package me.symi.owndrop.playerdata;

import me.symi.owndrop.drop.DropSettings;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private HashMap<UUID, DropSettings> player_data = new HashMap<>();

    public HashMap<UUID, DropSettings> getPlayer_data()
    {
        return player_data;
    }

    public void addPlayer(Player player, DropSettings dropSettings)
    {
        player_data.put(player.getUniqueId(), dropSettings);
    }

    public void removePlayer(Player player)
    {
        player_data.remove(player.getUniqueId());
    }

}
