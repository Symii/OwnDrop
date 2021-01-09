package me.symi.owndrop;

import me.symi.owndrop.commands.DropCommand;
import me.symi.owndrop.commands.TurboCommand;
import me.symi.owndrop.listeners.BlockListeners;
import me.symi.owndrop.listeners.InventoryListeners;
import me.symi.owndrop.listeners.PlayerListeners;
import me.symi.owndrop.manager.ConfigManager;
import me.symi.owndrop.manager.DropManager;
import me.symi.owndrop.manager.FileManager;
import me.symi.owndrop.metrics.MetricsLite;
import me.symi.owndrop.playerdata.PlayerDataManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main INSTANCE;
    private PlayerDataManager playerDataManager;
    private ConfigManager configManager;
    private FileManager fileManager;
    private DropManager dropManager;
    private boolean turbo_drop;

    @Override
    public void onLoad()
    {
        INSTANCE = this;
    }

    @Override
    public void onEnable()
    {
        getCommand("drop").setExecutor(new DropCommand());
        getCommand("turbo").setExecutor(new TurboCommand());
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryListeners(), this);
        pluginManager.registerEvents(new BlockListeners(this), this);
        pluginManager.registerEvents(new PlayerListeners(this), this);

        playerDataManager = new PlayerDataManager();
        configManager = new ConfigManager(this);
        fileManager = new FileManager(this);
        dropManager = new DropManager(this);

        if(configManager.isMetrics())
        {
            new MetricsLite(this, 9926);
        }
    }

    @Override
    public void onDisable()
    {
        if(playerDataManager != null)
            playerDataManager.onDisable();
    }

    public PlayerDataManager getPlayerDataManager()
    {
        return playerDataManager;
    }

    public static Main getInstance()
    {
        return INSTANCE;
    }

    public ConfigManager getConfigManager()
    {
        return configManager;
    }

    public FileManager getFileManager()
    {
        return fileManager;
    }

    public DropManager getDropManager()
    {
        return dropManager;
    }

    public boolean isTurbo_drop() {
        return turbo_drop;
    }

    public void setTurbo_drop(boolean turbo_drop) {
        this.turbo_drop = turbo_drop;
    }
}
