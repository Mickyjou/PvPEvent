package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.EventTeam;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Mickyjou on 14.01.2017.
 */
public class TeamCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (args.length == 0) {
            if (Utils.hasTeam(p)) {

                String team = Utils.getTeam(p.getUniqueId());
                p.sendMessage(PvPEventPlugin.prefix + "Du befindest dich im Team §6" + team + "§7.");
                UUID uuid = Utils.getTeamMate(p.getUniqueId());
                OfflinePlayer mate = Bukkit.getOfflinePlayer(uuid);
                p.sendMessage(PvPEventPlugin.prefix + "Dein Mitspieler ist§6 " +
                        mate.getName() + "§7.");

            } else {
                p.sendMessage(PvPEventPlugin.prefix + "Du befindest dich in keinem Team!");
            }

            // /team create [name] [player1] [player2]
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("create")) {
                if (p.isOp()) {
                    String name = args[1];
                    OfflinePlayer p1 = Bukkit.getOfflinePlayer(args[2]);
                    OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[3]);
                    EventTeam team = new EventTeam(name, p1.getUniqueId().toString(), p2.getUniqueId().toString());
                    team.save();
                    p.sendMessage(PvPEventPlugin.prefix + "Du hast Erfolgreich das Team erstellt.");
                }else{
                    p.sendMessage(PvPEventPlugin.prefix + "Du bist nicht OP!");
                }
            } else {
                p.sendMessage(PvPEventPlugin.prefix + "/team create [name] [player1] [player2]");
            }
        } else {

            p.sendMessage(PvPEventPlugin.prefix + "/team");
            p.sendMessage(PvPEventPlugin.prefix + "/team create [name] [player1] [player2]");
        }

        return true;
    }
}
