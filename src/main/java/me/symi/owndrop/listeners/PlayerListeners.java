package me.symi.owndrop.listeners;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
import me.symi.owndrop.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {

    private final Main plugin;

    public PlayerListeners(Main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        PlayerDataManager manager = plugin.getPlayerDataManager();
        manager.addPlayer(player, new DropSettings(player, true, true, true, true));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();
        PlayerDataManager manager = plugin.getPlayerDataManager();
        manager.removePlayer(player);
    }

}
