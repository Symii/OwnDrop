package me.symi.owndrop.manager;

import me.symi.owndrop.Main;
import me.symi.owndrop.utils.ChatUtil;
import me.symi.owndrop.utils.RandomUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DropManager {

    private final Main plugin;
    private HashMap<ItemStack, Double> drop_items = new HashMap<>();
    private ArrayList<ItemStack> sorted_items = new ArrayList<>();

    public DropManager(Main plugin)
    {
        this.plugin = plugin;
        loadDropItems();
    }

    public void loadDropItems()
    {
        drop_items.clear();
        sorted_items.clear();
        FileConfiguration config = plugin.getFileManager().getDrop_config();
        for(String s : config.getConfigurationSection("drop").getKeys(false))
        {
            double chance = config.getDouble("drop." + s + ".chance");
            ItemStack item = new ItemStack(Material.valueOf(config.getString("drop." + s + ".material")), config.getInt("drop." + s + ".amount"));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatUtil.fixColors(config.getString("drop." + s + ".name")));
            item.setItemMeta(itemMeta);
            drop_items.put(item, chance);
            sorted_items.add(item);
        }
    }

    public ItemStack getDropItem()
    {
        double randomNum = RandomUtil.getRandomDouble(0, 100);
        for(ItemStack item : sorted_items)
        {
            double chance = drop_items.get(item);
            if(Main.getInstance().isTurbo_drop())
            {
                chance = chance * 2;
            }
            if(chance >= randomNum)
            {
                return item;
            }
        }
        return null;
    }

    public HashMap<ItemStack, Double> getDrop_items() {
        return drop_items;
    }

    public ArrayList<ItemStack> getSorted_items() {
        return sorted_items;
    }
}
