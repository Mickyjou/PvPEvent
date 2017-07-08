package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResponseCommand implements CommandExecutor
{


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

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
            String response = new StatsGetter(p).get(args[0]) != null ? new StatsGetter(p).get(args[0]) : "keine Angabe";
            p.sendMessage(PvPEventPlugin.prefix + "Ergebnis: §7" + response );


        }else if(args.length == 2){

            if(args[0].equalsIgnoreCase("delete")){
                StatsGetter stats = new StatsGetter(p);
                stats.deleteTimeTask(args[1]);
                p.sendMessage(PvPEventPlugin.prefix + "Succsesfully removed the entry " + args[1]);
            }

        }else{
            p.sendMessage(PvPEventPlugin.prefix + "/response [args0] [args1]");
        }





        return false;
    }
}
