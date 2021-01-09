package me.symi.owndrop.listeners;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
import me.symi.owndrop.manager.ConfigManager;
import me.symi.owndrop.manager.DropManager;
import me.symi.owndrop.utils.DropToInvUtil;
import me.symi.owndrop.utils.RandomUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BlockListeners implements Listener {

    private final Main plugin;

    public BlockListeners(Main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        if(event.getBlock().getType() == Material.STONE)
        {
            final Location block_location = event.getBlock().getLocation();
            final Player player = event.getPlayer();
            final DropSettings dropSettings = plugin.getPlayerDataManager().getDropSettings(player);
            final ConfigManager configManager = plugin.getConfigManager();
            final DropManager dropManager = plugin.getDropManager();
            List<ItemStack> drops = dropManager.getDrops(player);

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

            if(drops.size() >= 1)
            {
                boolean item_dropped = false;
                for(ItemStack item : drops)
                {
                    if(dropSettings.isDropDisabled(item))
                    {
                        continue;
                    }
                    if(dropSettings.isMessages())
                    {
                        player.sendMessage(configManager.getDrop_message()
                                .replace("%item%", item.getItemMeta().getDisplayName())
                                .replace("%amount%", String.valueOf(item.getAmount())));
                    }

                    ItemStack drop_item = new ItemStack(item.getType(), item.getAmount());
                    DropToInvUtil.dropItem(player, drop_item, block_location);
                    item_dropped = true;
                }

                if(dropSettings.isSounds() && item_dropped)
                {
                    player.playSound(player.getLocation(), configManager.getDrop_sound(), 1.0f, 1.0f);
                }

            }
        }
    }

}
