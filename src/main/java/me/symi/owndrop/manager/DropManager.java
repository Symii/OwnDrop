package me.symi.owndrop.manager;

import me.symi.owndrop.Main;
import me.symi.owndrop.owndrop.DropItem;
import me.symi.owndrop.utils.ChatUtil;
import me.symi.owndrop.utils.RandomUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DropManager {

    private final Main plugin;
    private ArrayList<DropItem> sorted_items = new ArrayList<>();

    public DropManager(Main plugin)
    {
        this.plugin = plugin;
        loadDropItems();
    }

    public void loadDropItems()
    {
        sorted_items.clear();
        FileConfiguration config = plugin.getFileManager().getDrop_config();
        for(String s : config.getConfigurationSection("drop").getKeys(false))
        {
            double chance = config.getDouble("drop." + s + ".chance");
            boolean custom_name_enabled = config.getBoolean("drop." + s + ".custom-name-enabled");
            Material material = Material.valueOf(config.getString("drop." + s + ".material").toUpperCase());
            int amount = config.getInt("drop." + s + ".amount");
            List<String> lore = new ArrayList<>();
            String itemName = ChatUtil.fixColors(config.getString("drop." + s + ".name"));

            if(custom_name_enabled)
            {
                lore = ChatUtil.fixColors(config.getStringList("drop." + s + ".lore"));
            }

            DropItem dropItem = new DropItem(lore, custom_name_enabled, chance, amount, material, itemName);
            sorted_items.add(dropItem);
        }
    }

    public List<DropItem> getDropItem()
    {
        List<DropItem> drops = new ArrayList<>();
        for(DropItem dropItem : sorted_items)
        {
            double randomNum = RandomUtil.getRandomDouble(0, 100);
            double chance = dropItem.getChance();
            if(Main.getInstance().isTurbo_drop())
            {
                chance = chance * 2;
            }

            if(randomNum <= chance)
            {
                drops.add(dropItem);
            }
        }
        return drops;
    }

    public ArrayList<DropItem> getSorted_items() {
        return sorted_items;
    }
}
