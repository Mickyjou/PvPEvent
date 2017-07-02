package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CompassCommand implements CommandExecutor {



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

        if(args.length == 1){

            switch (args[0]){
                case "jnr":
                case "recipes":
                case "zombie":
                case "stats":
                    Utils.saveLocation(p.getLocation(), "compass" + args[0].toLowerCase());
                    p.sendMessage(PvPEventPlugin.prefix + "Succsesfully saved the location.");
                    break;

                default:
                    p.sendMessage("/compass (jnr/recipes/zombie/stats)");
                    break;
            }


        }else{

            p.sendMessage("/compass (jnr/recipes/zombie/stats)");
        }


        return false;
    }
}
