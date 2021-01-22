package me.symi.owndrop.listeners;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
import me.symi.owndrop.manager.ConfigManager;
import me.symi.owndrop.manager.DropManager;
import me.symi.owndrop.owndrop.DropItem;
import me.symi.owndrop.utils.DropToInvUtil;
import me.symi.owndrop.utils.RandomUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockListeners implements Listener {

    private final Main plugin;

    public BlockListeners(Main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event)
    {
        if(event.getBlock().getType() == Material.STONE)
        {
            final String world = event.getBlock().getWorld().getName();
            boolean drop_enabled = false;
            for(String world_name : Main.getInstance().getConfigManager().getEnabled_worlds())
            {
                if(world.equalsIgnoreCase(world_name))
                {
                    drop_enabled = true;
                    break;
                }
            }

            if(!drop_enabled)
                return;

            final Location block_location = event.getBlock().getLocation();
            final Player player = event.getPlayer();
            final DropSettings dropSettings = plugin.getPlayerDataManager().getDropSettings(player);
            final ConfigManager configManager = plugin.getConfigManager();
            final DropManager dropManager = plugin.getDropManager();

            if(dropSettings.isCobblestone_drop() == false)
            {
                event.setDropItems(false);
            }

            double randomNum = RandomUtil.getRandomDouble(0, 100);
            if(dropSettings.isExp_drop() && configManager.getExp_drop_chance() >= randomNum)
            {
                ExperienceOrb orb = (ExperienceOrb) player.getWorld().spawnEntity(player.getLocation(), EntityType.EXPERIENCE_ORB);
                orb.setExperience(configManager.getExp_drop_amount());
            }

            if(dropManager.getDropItem(player).size() <= 0)
            {
                return;
            }

            for(DropItem dropItem : dropManager.getDropItem(player))
            {
                ItemStack item = dropItem.parseItem();
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(dropItem.getItemName());
                item.setItemMeta(itemMeta);

                if(dropSettings.isDropDisabled(item))
                {
                    continue;
                }
                if(dropSettings.isMessages())
                {
                    player.sendMessage(configManager.getDrop_message()
                            .replace("%item%", dropItem.getItemName())
                            .replace("%amount%", String.valueOf(dropItem.getAmount())));
                }

                ItemStack drop_item = dropItem.parseItem();
                DropToInvUtil.dropItem(player, drop_item, block_location);
                if(dropSettings.isSounds())
                {
                    player.playSound(player.getLocation(), configManager.getDrop_sound(), 1.0f, 1.0f);
                }
            }
        }
    }

}
