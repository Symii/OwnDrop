package me.symi.owndrop.manager;

import me.symi.owndrop.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class DropManager {

    private final Main plugin;
    private HashMap<ItemStack, Double> drop_items = new HashMap<>();

    public DropManager(Main plugin)
    {
        this.plugin = plugin;
        loadDropItems();
    }

    private void loadDropItems()
    {
        FileConfiguration config = plugin.getFileManager().getDrop_config();
        for(String s : config.getConfigurationSection("drop").getKeys(false))
        {
            double chance = config.getDouble("drop." + s + ".chance");
            ItemStack item = new ItemStack(Material.valueOf(config.getString("drop." + s + ".material")), config.getInt("drop." + s + ".amount"));
            drop_items.put(item, chance);
        }
    }

    public ItemStack getDropItem(Player player)
    {
        double randomNum = ThreadLocalRandom.current().nextDouble(0, 100 + 1);
        for(ItemStack item : drop_items.keySet())
        {
            double chance = drop_items.get(item);
            if(chance >= randomNum)
            {
                return item;
            }
        }
        return null;
    }

}
