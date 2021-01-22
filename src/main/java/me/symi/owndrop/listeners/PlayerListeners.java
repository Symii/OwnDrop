package me.symi.owndrop.listeners;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
import me.symi.owndrop.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement
                            ("SELECT data FROM players WHERE playername=?");
                    statement.setString(1, player.getName());
                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next())
                    {
                        String data = resultSet.getString("data");
                        manager.addPlayer(player, new DropSettings(player, data));
                    }
                    else
                    {
                        manager.addPlayer(player, new DropSettings(player, "true;true;true;true;"));
                        plugin.getPlayerDataManager().getDropSettings(player).createPlayer();
                    }
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();
        PlayerDataManager manager = plugin.getPlayerDataManager();
        manager.getDropSettings(player).saveAsync();
        manager.removePlayer(player);
    }

}
