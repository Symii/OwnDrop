package me.symi.owndrop;

import me.symi.owndrop.commands.DropCommand;
import me.symi.owndrop.listeners.BlockListeners;
import me.symi.owndrop.listeners.InventoryListeners;
import me.symi.owndrop.metrics.MetricsLite;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable()
    {
        getCommand("drop").setExecutor(new DropCommand());
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryListeners(), this);
        pluginManager.registerEvents(new BlockListeners(this), this);
        new MetricsLite(this, 9926);
    }

    @Override
    public void onDisable()
    {

    }

}
