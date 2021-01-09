package me.symi.owndrop.commands;

import me.symi.owndrop.Main;
import me.symi.owndrop.gui.DropGUI;
import me.symi.owndrop.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage("Nie mozesz tego uzywac");
            return true;
        }

        final Player player = (Player) sender;

        if(player.hasPermission("owndrop.reload") && args.length == 1 && args[0].equalsIgnoreCase("reload"))
        {
            sender.sendMessage(ChatUtil.fixColors("&aPrzeladowano config pluginu OwnDrop"));
            Main.getInstance().getFileManager().reloadConfig();
            Main.getInstance().getConfigManager().loadConfig();
            Main.getInstance().getDropManager().loadDropItems();
            return true;
        }


        player.openInventory(DropGUI.getMainInventory());

        return false;
    }

}
