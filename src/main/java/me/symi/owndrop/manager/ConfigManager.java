package me.symi.owndrop.manager;

import me.symi.owndrop.Main;
import me.symi.owndrop.utils.ChatUtil;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    private final Main plugin;
    private Sound drop_sound;
    private boolean metrics;
    private double exp_drop_chance;
    private int exp_drop_amount;
    private String drop_message;
    private List<String> enabled_worlds;

    public ConfigManager(Main plugin)
    {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        loadConfig();
    }

    public void loadConfig()
    {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        drop_sound = Sound.valueOf(config.getString("drop-sound"));
        metrics = config.getBoolean("metrics");
        exp_drop_chance = config.getDouble("exp-drop-chance");
        exp_drop_amount = config.getInt("exp-drop-amount");
        drop_message = ChatUtil.fixColors(config.getString("drop-message"));
        enabled_worlds = config.getStringList("enabled-worlds");
    }

    public String getDrop_message() {
        return drop_message;
    }

    public Sound getDrop_sound() {
        return drop_sound;
    }

    public boolean isMetrics() {
        return metrics;
    }

    public double getExp_drop_chance() {
        return exp_drop_chance;
    }

    public int getExp_drop_amount() {
        return exp_drop_amount;
    }

    public List<String> getEnabled_worlds() {
        return enabled_worlds;
    }
}
