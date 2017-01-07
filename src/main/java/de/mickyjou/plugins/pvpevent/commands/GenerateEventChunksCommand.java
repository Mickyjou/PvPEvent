package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Michi on 07.01.2017.
 */
public class GenerateEventChunksCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You have to be a Player to execute this command");
            return true;
        }

        Player p = (Player) sender;
        if (args.length == 1) {
            int radius;

            try {
                radius = Integer.valueOf(args[0]);

            } catch (Exception e) {
                p.sendMessage(PvPEventPlugin.prefix + "Bitte gib eine gültige Zahl ein.");
                return true;
            }
            if(Math.round(radius / 4) == radius/4) {
                Utils.generateEventChunks(radius, p.getWorld());
                p.sendMessage(PvPEventPlugin.prefix + "Erfolgreich " + ChatColor.GOLD + radius/4 + ChatColor.GRAY + "EventChunks erstellt!");
                return true;
            }else{
                p.sendMessage(PvPEventPlugin.prefix + "Bitte gib eine Zahl ein, die durch vier geteilt grade ist.");
            }


            return true;

        }
        return false;
    }
}
