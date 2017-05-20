package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZombieCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PvPEventPlugin.prefix + "You have to be a Player to execute this command.");
            return true;
        }
        Player p = (Player) sender;

        if (args.length == 0) {
            if (p.isOp()) {
                Utils.saveLocation(p.getLocation(), "zombie");
                p.sendMessage(PvPEventPlugin.prefix + "Erfolgreich den Zombiespawn gesetzt.");
            }
        } else {
            p.sendMessage(PvPEventPlugin.prefix + "/setzombie");
        }

        return false;
    }
}
