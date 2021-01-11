package me.symi.owndrop.owndrop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DropItem {

    private List<String> lore;
    private boolean custom_name_enabled;
    private double chance;
    private int amount;
    private Material material;
    private String itemName;

    public DropItem(List<String> lore, boolean custom_name_enabled, double chance, int amount, Material material, String itemName)
    {
        this.lore = lore;
        this.custom_name_enabled = custom_name_enabled;
        this.chance = chance;
        this.amount = amount;
        this.material = material;
        this.itemName = itemName;
    }

    public ItemStack parseItem()
    {
        ItemStack item = new ItemStack(material, amount);

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

    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return material;
    }
}
