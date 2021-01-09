package me.symi.owndrop.listeners;

import me.symi.owndrop.Main;
import me.symi.owndrop.drop.DropSettings;
import me.symi.owndrop.manager.ConfigManager;
import me.symi.owndrop.manager.DropManager;
import me.symi.owndrop.utils.ChatUtil;
import me.symi.owndrop.utils.DropToInvUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

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
            ItemStack drop_item = dropManager.getDropItem(player);

            if(dropSettings.isCobblestone_drop() == false)
            {
                event.setDropItems(false);
            }

            if(drop_item != null)
            {
                if(dropSettings.isSounds())
                {
                    player.playSound(player.getLocation(), configManager.getDrop_sound(), 1.0f, 1.0f);
                }
                if(dropSettings.isMessages())
                {
                    player.sendMessage(configManager.getDrop_message()
                            .replace("%item%", drop_item.getType().toString())
                            .replace("%amount%", String.valueOf(drop_item.getAmount())));
                }
                if(dropSettings.isExp_drop())
                {
                    ExperienceOrb orb = (ExperienceOrb) block_location.getWorld().spawnEntity(block_location, EntityType.EXPERIENCE_ORB);
                    orb.setCustomName(ChatUtil.fixColors("&aExp x " + configManager.getExp_drop_amount()));
                    orb.setCustomNameVisible(true);
                    orb.setExperience(configManager.getExp_drop_amount());
                }

                DropToInvUtil.dropItem(player, drop_item, block_location);
            }
        }
    }

}
