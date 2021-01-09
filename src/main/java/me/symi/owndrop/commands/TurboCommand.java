package me.symi.owndrop.commands;

import me.symi.owndrop.Main;
import me.symi.owndrop.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class TurboCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(sender.hasPermission("owndrop.turbo.enable"))
        {
            if(args.length != 1)
            {
                sender.sendMessage(ChatUtil.fixColors("&eDrop &8► &cPoprawne uzycie: /turbo <czas w minutach>"));
                return true;
            }

            int minutes = 0;
            try
            {
                minutes = Integer.parseInt(args[0]);
                if(minutes >= 61 || minutes <= 0)
                {
                    sender.sendMessage(ChatUtil.fixColors("&eDrop &8► &cpodaj liczbe calkowita od 1 do 60 (minuty)"));
                    return true;
                }
                if(Main.getInstance().isTurbo_drop())
                {
                    sender.sendMessage(ChatUtil.fixColors("&eDrop &8► &cturbo drop jest juz wlaczony"));
                    return true;
                }
                sender.sendMessage(ChatUtil.fixColors("&eDrop &8► &auruchomiles turbo drop na &e" + minutes + " minut"));
                Bukkit.broadcastMessage(ChatUtil.fixColors("&cTurbo Drop &7został uruchomiony przez &e" + sender.getName() + " &7na &e" + minutes + " minut!"));
                Main.getInstance().setTurbo_drop(true);
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        Main.getInstance().setTurbo_drop(false);
                    }
                }.runTaskLater(Main.getInstance(), 20 * 60 * minutes);
            }
            catch(Exception e)
            {
                sender.sendMessage(ChatUtil.fixColors("&eDrop &8► &cpodaj liczbe calkowita od 1 do 60 (minuty)"));
                return true;
            }
        }
        else
        {
            sender.sendMessage(ChatUtil.fixColors("&eDrop &8► &cnie posiadasz permisji &eowndrop.turbo.enable"));
        }

        return false;
    }
}
