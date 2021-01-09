package me.symi.owndrop.manager;

import me.symi.owndrop.Main;
import me.symi.owndrop.utils.ChatUtil;
import me.symi.owndrop.utils.RandomUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatUtil.fixColors(config.getString("drop." + s + ".name")));
            item.setItemMeta(itemMeta);
            drop_items.put(item, chance);
        }
    }

    public List<ItemStack> getDrops(Player player)
    {
        List<ItemStack> drops = new ArrayList<>();
        double randomNum = RandomUtil.getRandomDouble(0, 100);
        double global_chance = 0;
        for(ItemStack item : drop_items.keySet())
        {
            double chance = drop_items.get(item);
            if(Main.getInstance().isTurbo_drop())
            {
                chance = chance * 2;
            }
            if(chance >= (randomNum + global_chance))
            {
                drops.add(item);
                global_chance += randomNum;
            }
        }
        return drops;
    }

    public HashMap<ItemStack, Double> getDrop_items() {
        return drop_items;
    }
}
