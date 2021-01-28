package me.symi.owndrop.owndrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class DropItem {

    private List<String> lore;
    private boolean custom_name_enabled;
    private double chance;
    private Material material;
    private String itemName;

    private int fortune0min, fortune0max;
    private int fortune1min, fortune1max;
    private int fortune2min, fortune2max;
    private int fortune3min, fortune3max;

    public DropItem(List<String> lore, boolean custom_name_enabled, double chance, Material material, String itemName, String fortune0, String fortune1, String fortune2, String fortune3)
    {
        this.lore = lore;
        this.custom_name_enabled = custom_name_enabled;
        this.chance = chance;
        this.material = material;
        this.itemName = itemName;

        this.fortune0min = Integer.parseInt(fortune0.split("-")[0]);
        this.fortune0max = Integer.parseInt(fortune0.split("-")[1]);
        this.fortune1min = Integer.parseInt(fortune1.split("-")[0]);
        this.fortune1max = Integer.parseInt(fortune1.split("-")[1]);

        this.fortune2min = Integer.parseInt(fortune2.split("-")[0]);
        this.fortune2max = Integer.parseInt(fortune2.split("-")[1]);
        this.fortune3min = Integer.parseInt(fortune3.split("-")[0]);
        this.fortune3max = Integer.parseInt(fortune3.split("-")[1]);
    }

    private int getDropAmount(Player player)
    {
        ItemStack item = player.getInventory().getItemInMainHand();
        int amount = getAmount(fortune0min, fortune0max);

        if(item != null && item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS))
        {
            int loot_bonus_blocks = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            if(loot_bonus_blocks == 1)
            {
                amount = getAmount(fortune1min, fortune1max);
            }
            else if(loot_bonus_blocks == 2)
            {
                amount = getAmount(fortune2min, fortune2max);
            }
            else if(loot_bonus_blocks >= 3)
            {
                amount = getAmount(fortune3min, fortune3max);
            }
        }

        return amount;
    }

    private int getAmount(int min, int max)
    {
        int amount = min;
        int max_min = max - min;
        if(max_min >= 2)
        {
            amount = new Random().nextInt(max_min) + min;
        }

        return amount;
    }

    public ItemStack parseItem(Player player)
    {
        ItemStack item = new ItemStack(material, getDropAmount(player));

        if(custom_name_enabled)
        {
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(itemName);
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        }

        return item;
    }

    public String getItemName() {
        return itemName;
    }

    public List<String> getLore() {
        return lore;
    }

    public boolean isCustom_name_enabled() {
        return custom_name_enabled;
    }

    public double getChance() {
        return chance;
    }

    public Material getMaterial() {
        return material;
    }

    public int getFortune0min() {
        return fortune0min;
    }

    public int getFortune0max() {
        return fortune0max;
    }

    public int getFortune1min() {
        return fortune1min;
    }

    public int getFortune1max() {
        return fortune1max;
    }

    public int getFortune2min() {
        return fortune2min;
    }

    public int getFortune2max() {
        return fortune2max;
    }

    public int getFortune3min() {
        return fortune3min;
    }

    public int getFortune3max() {
        return fortune3max;
    }
}
