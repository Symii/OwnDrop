package me.symi.owndrop.manager;

import me.symi.owndrop.Main;
import me.symi.owndrop.utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    private final Main plugin;
    private Sound drop_sound;
    private boolean metrics;
    private double exp_drop_chance;
    private int exp_drop_amount;
    private String drop_message, turbo_drop_message;
    private List<String> enabled_worlds;
    private HashMap<String, Double> multipilers = new HashMap<>();
    private int drop_settings_inventory_size;
    private String drop_options_gui_title, drop_settings_gui_title, drop_menu_gui_title;

    private ItemStack drop_settings_item, drop_options_item, turbo_drop_item;
    private Material drop_menu_gui_fill_item_material, drop_settings_gui_fill_item_material;

    private ItemStack exp_drop_item, messages_item, sounds_item, cobblestone_drop_item;
    private ItemStack enable_all_item, disable_all_item;
    private List<String> drop_item_lore;
    private String cross_mark, check_mark;

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
        List<String> drop_multipiler = config.getStringList("drop-multipiler");
        for(String s : drop_multipiler)
        {
            String[] array = s.split(";");
            multipilers.put(array[0], Double.parseDouble(array[1]));
        }
        drop_settings_inventory_size = config.getInt("drop-settings-inventory-size");

        drop_options_gui_title = ChatUtil.fixColors(config.getString("drop-options-gui-title"));
        drop_settings_gui_title = ChatUtil.fixColors(config.getString("drop-settings-gui-title"));
        drop_menu_gui_title = ChatUtil.fixColors(config.getString("drop-menu-gui-title"));

        disable_all_item = new ItemStack(Material.valueOf(config.getString("drop-options-gui-items.disable-all.material").toUpperCase()));
        ItemMeta disable_all_item_meta = disable_all_item.getItemMeta();
        disable_all_item_meta.setDisplayName(ChatUtil.fixColors(config.getString("drop-options-gui-items.disable-all.name")));
        disable_all_item_meta.setLore(ChatUtil.fixColors(config.getStringList("drop-options-gui-items.disable-all.lore")));
        disable_all_item.setItemMeta(disable_all_item_meta);

        enable_all_item = new ItemStack(Material.valueOf(config.getString("drop-options-gui-items.enable-all.material").toUpperCase()));
        ItemMeta enable_all_item_meta = enable_all_item.getItemMeta();
        enable_all_item_meta.setDisplayName(ChatUtil.fixColors(config.getString("drop-options-gui-items.enable-all.name")));
        enable_all_item_meta.setLore(ChatUtil.fixColors(config.getStringList("drop-options-gui-items.enable-all.lore")));
        enable_all_item.setItemMeta(enable_all_item_meta);

        turbo_drop_item = new ItemStack(Material.valueOf(config.getString("main-menu-gui-items.turbo-drop-item.material").toUpperCase()));
        ItemMeta turbo_drop_item_meta = turbo_drop_item.getItemMeta();
        turbo_drop_item_meta.setDisplayName(ChatUtil.fixColors(config.getString("main-menu-gui-items.turbo-drop-item.name")));
        turbo_drop_item_meta.setLore(ChatUtil.fixColors(config.getStringList("main-menu-gui-items.turbo-drop-item.lore")));
        turbo_drop_item.setItemMeta(turbo_drop_item_meta);

        drop_settings_item = new ItemStack(Material.valueOf(config.getString("main-menu-gui-items.drop-settings-item.material").toUpperCase()));
        ItemMeta drop_settings_item_meta = drop_settings_item.getItemMeta();
        drop_settings_item_meta.setDisplayName(ChatUtil.fixColors(config.getString("main-menu-gui-items.drop-settings-item.name")));
        drop_settings_item_meta.setLore(ChatUtil.fixColors(config.getStringList("main-menu-gui-items.drop-settings-item.lore")));
        drop_settings_item.setItemMeta(drop_settings_item_meta);

        drop_options_item = new ItemStack(Material.valueOf(config.getString("main-menu-gui-items.drop-options-item.material").toUpperCase()));
        ItemMeta drop_options_item_meta = drop_options_item.getItemMeta();
        drop_options_item_meta.setDisplayName(ChatUtil.fixColors(config.getString("main-menu-gui-items.drop-options-item.name")));
        drop_options_item_meta.setLore(ChatUtil.fixColors(config.getStringList("main-menu-gui-items.drop-options-item.lore")));
        drop_options_item.setItemMeta(drop_options_item_meta);

        drop_menu_gui_fill_item_material = Material.valueOf(config.getString("drop-menu-gui-fill-item-material").toUpperCase());
        drop_settings_gui_fill_item_material = Material.valueOf(config.getString("drop-settings-gui-fill-item-material").toUpperCase());

        cobblestone_drop_item = new ItemStack(Material.valueOf(config.getString("drop-settings-gui-items.cobblestone-drop.material").toUpperCase()));
        ItemMeta cobblestone_drop_item_meta = cobblestone_drop_item.getItemMeta();
        cobblestone_drop_item_meta.setDisplayName(ChatUtil.fixColors(config.getString("drop-settings-gui-items.cobblestone-drop.name")));
        cobblestone_drop_item_meta.setLore(ChatUtil.fixColors(config.getStringList("drop-settings-gui-items.cobblestone-drop.lore")));
        cobblestone_drop_item.setItemMeta(cobblestone_drop_item_meta);

        sounds_item = new ItemStack(Material.valueOf(config.getString("drop-settings-gui-items.sounds.material").toUpperCase()));
        ItemMeta sounds_item_meta = sounds_item.getItemMeta();
        sounds_item_meta.setDisplayName(ChatUtil.fixColors(config.getString("drop-settings-gui-items.sounds.name")));
        sounds_item_meta.setLore(ChatUtil.fixColors(config.getStringList("drop-settings-gui-items.sounds.lore")));
        sounds_item.setItemMeta(sounds_item_meta);

        messages_item = new ItemStack(Material.valueOf(config.getString("drop-settings-gui-items.messages.material").toUpperCase()));
        ItemMeta messages_item_meta = messages_item.getItemMeta();
        messages_item_meta.setDisplayName(ChatUtil.fixColors(config.getString("drop-settings-gui-items.messages.name")));
        messages_item_meta.setLore(ChatUtil.fixColors(config.getStringList("drop-settings-gui-items.messages.lore")));
        messages_item.setItemMeta(messages_item_meta);

        exp_drop_item = new ItemStack(Material.valueOf(config.getString("drop-settings-gui-items.exp-drop.material").toUpperCase()));
        ItemMeta exp_drop_item_meta = exp_drop_item.getItemMeta();
        exp_drop_item_meta.setDisplayName(ChatUtil.fixColors(config.getString("drop-settings-gui-items.exp-drop.name")));
        exp_drop_item_meta.setLore(ChatUtil.fixColors(config.getStringList("drop-settings-gui-items.exp-drop.lore")));
        exp_drop_item.setItemMeta(exp_drop_item_meta);

        turbo_drop_message = ChatUtil.fixColors(config.getString("turbo-drop-message"));
        drop_item_lore = ChatUtil.fixColors(config.getStringList("drop-item-lore"));

        cross_mark = ChatUtil.fixColors(config.getString("cross-mark"));
        check_mark = ChatUtil.fixColors(config.getString("check-mark"));
    }

    public String getCross_mark() {
        return cross_mark;
    }

    public String getCheck_mark() {
        return check_mark;
    }

    public List<String> getDrop_item_lore() {
        return drop_item_lore;
    }

    public ItemStack getEnable_all_item() {
        return enable_all_item;
    }

    public ItemStack getDisable_all_item() {
        return disable_all_item;
    }

    public ItemStack getTurbo_drop_item() {
        return turbo_drop_item;
    }

    public String getTurbo_drop_message() {
        return turbo_drop_message;
    }

    public ItemStack getExp_drop_item() {
        return exp_drop_item;
    }

    public ItemStack getMessages_item() {
        return messages_item;
    }

    public ItemStack getSounds_item() {
        return sounds_item;
    }

    public ItemStack getCobblestone_drop_item() {
        return cobblestone_drop_item;
    }

    public Material getDrop_menu_gui_fill_item_material() {
        return drop_menu_gui_fill_item_material;
    }

    public Material getDrop_settings_gui_fill_item_material() {
        return drop_settings_gui_fill_item_material;
    }

    public ItemStack getDrop_settings_item() {
        return drop_settings_item;
    }

    public ItemStack getDrop_options_item() {
        return drop_options_item;
    }

    public String getDrop_options_gui_title() {
        return drop_options_gui_title;
    }

    public String getDrop_settings_gui_title() {
        return drop_settings_gui_title;
    }

    public String getDrop_menu_gui_title() {
        return drop_menu_gui_title;
    }

    public int getDrop_settings_inventory_size() {
        return drop_settings_inventory_size;
    }

    public HashMap<String, Double> getMultipilers()
    {
        return multipilers;
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
