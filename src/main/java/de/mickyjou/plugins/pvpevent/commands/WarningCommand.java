package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarningCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof  Player)){
            sender.sendMessage(PvPEventPlugin.prefix + "You have to be a Player to execute this command.");
            return true;
        }
        if (args.length == 0) {
                //show warnings
                Player p = (Player) sender;
                StatsGetter stats = new StatsGetter(p);
                p.sendMessage(PvPEventPlugin.prefix + "Du hast bereits §6" + stats.getWarnings() + " §7Verwarnungen");



            //warnings add Player
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {

                String name = args[1];
                OfflinePlayer p = Bukkit.getOfflinePlayer(name);
                if (p != null) {
                    StatsGetter stats = new StatsGetter(p);
                    stats.addWarning();
                    sender.sendMessage(PvPEventPlugin.prefix + "Du hast den Spieler §6" + p.getName() + " §7erfolgreich gewarnt");
                } else {
                    sender.sendMessage(PvPEventPlugin.prefix + "Der Spieler existiert nicht!");
                }
            } else if (args[0].equalsIgnoreCase("remove")) {

                String name = args[1];
                OfflinePlayer p = Bukkit.getOfflinePlayer(name);
                if (p != null) {
                    StatsGetter stats = new StatsGetter(p);
                    stats.removeWarning();
                    sender.sendMessage(PvPEventPlugin.prefix + "Du hast den Spieler §6" + p.getName() + " §7erfolgreich eine Warnung entfernt.");
                } else {
                    sender.sendMessage(PvPEventPlugin.prefix + "Der Spieler existiert nicht!");
                }

            } else {
                sender.sendMessage(PvPEventPlugin.prefix + "/warnings add [playername]");
            }
        } else {
            sender.sendMessage(PvPEventPlugin.prefix + "/warnings");
            sender.sendMessage(PvPEventPlugin.prefix + "/warnings add [Playername]");
        }

        return false;
    }
}
