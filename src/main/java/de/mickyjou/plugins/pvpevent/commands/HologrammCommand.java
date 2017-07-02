package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class HologrammCommand implements CommandExecutor {

    public static HashSet<Player> players = new HashSet<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PvPEventPlugin.prefix + "You have to be a Player to execute this command.");
            return true;
        }
        Player p = (Player) sender;

        if(!p.isOp()) {
            p.sendMessage(PvPEventPlugin.prefix + "You've to be OP to execute this command.");
            return true;
        }

        if(args.length == 0){
                players.add(p);
                p.sendMessage(PvPEventPlugin.prefix + "Click on a Block to save the Location.");

        }else{
            p.sendMessage(PvPEventPlugin.prefix + "/sethologramm");
        }

        return false;
    }
}
