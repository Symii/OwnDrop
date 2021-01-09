package me.symi.owndrop.manager;

import me.symi.owndrop.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private final Main plugin;
    private FileConfiguration drop_config;
    private File drop_file;

    public FileManager(Main plugin)
    {
        this.plugin = plugin;
        createConfig();
    }

    private void createConfig()
    {
        drop_file = new File(plugin.getDataFolder() + File.separator + "drops", "drop.yml");
        if(!drop_file.exists())
        {
            drop_file.getParentFile().mkdir();
            plugin.saveResource("drop.yml", false);
            File createdFile = new File(plugin.getDataFolder(), "drop.yml");
            createdFile.renameTo(new File(plugin.getDataFolder() + File.separator + "drops" + File.separator + "drop.yml"));
        }

        drop_config = new YamlConfiguration();
        reloadConfig();
    }

    public void reloadConfig()
    {
        try
        {
            drop_config.load(drop_file);
        }
        catch(IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public File getDrop_file(String language)
    {
        return drop_file;
    }

    public FileConfiguration getDrop_config()
    {
        return drop_config;
    }

}
