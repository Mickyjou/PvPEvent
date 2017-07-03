package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


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
            if(args[0].equalsIgnoreCase("setfirst")){
                Utils.saveLocation(p.getLocation(), "npcfirst");
                p.sendMessage(PvPEventPlugin.prefix + "Succesfully saved the Location for the first NPC.");

            }else if(args[0].equalsIgnoreCase("setsecond")){

                Utils.saveLocation(p.getLocation(), "npcsecond");
                p.sendMessage(PvPEventPlugin.prefix + "Succesfully saved the Location for the second NPC.");
            }else if(args[0].equalsIgnoreCase("stats")){
                p.sendMessage(PvPEventPlugin.prefix + "Succsesfully saved the Location for the Stats-Hologram");
                Utils.saveLocation(p.getLocation(),"npcstats");
            }
            else{
                p.sendMessage(PvPEventPlugin.prefix + "/npc setfirst / setsecond /stats");
            }

        }else{
            p.sendMessage(PvPEventPlugin.prefix + "/npc setfirst / setsecond /stats");
        }




        return false;
    }
}
