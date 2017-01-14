package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.EventTeam;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * Created by Mickyjou on 14.01.2017.
 */
public class TeamsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            ArrayList<EventTeam> teams = Utils.getAllTeams();
            sender.sendMessage(PvPEventPlugin.prefix + " ==== TEAMS ====");

            for (EventTeam all : teams) {
                sender.sendMessage("Team §6" + all.getName());
                sender.sendMessage("Mitspieler: §6" + all.getPlayers()[0].getName()
                        + " §7und §6" + all.getPlayers()[1].getName());
                sender.sendMessage("  ");
            }
        }else{
            sender.sendMessage("/teams");
        }
        return true;
    }
}
