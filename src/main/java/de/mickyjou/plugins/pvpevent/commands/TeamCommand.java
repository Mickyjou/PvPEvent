package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.EventTeam;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
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
        if(!p.isOp()){
            p.sendMessage(PvPEventPlugin.prefix + "You do not have enough Permissions to execute this command");
            return true;
        }

        if (args.length == 0) {
            StatsGetter stats = new StatsGetter(p);
            if (stats.hasTeam()) {


                String team = stats.getTeam();
                p.sendMessage(PvPEventPlugin.prefix + "Du befindest dich im Team §6" + team + "§7.");
                UUID uuid = stats.getTeamMate();
                OfflinePlayer mate = Bukkit.getOfflinePlayer(uuid);
                StatsGetter stats2 = new StatsGetter(mate);
                p.sendMessage(PvPEventPlugin.prefix + "Dein Mitspieler ist§6 " +
                        mate.getName() + "§7.");
                p.sendMessage(PvPEventPlugin.prefix + "Zusammen habt ihr §6" +
                        (stats2.getKills() + stats.getKills())
                        + " §7Kills.");
                if (!stats2.isAlive()) {
                    p.sendMessage(PvPEventPlugin.prefix + "Dein Mitspieler ist bereits gestorben.");
                }


            } else {
                p.sendMessage(PvPEventPlugin.prefix + "Du befindest dich in keinem Team!");
            }

            // /team create [name] [player1] [player2]
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("create")) {
                if (p.isOp()) {
                    String name = args[1];
                    OfflinePlayer p1 = Bukkit.getOfflinePlayer(args[2]);
                    StatsGetter stats1 = new StatsGetter(p1);
                    StatsGetter stats2 = new StatsGetter(p1);
                    OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[3]);
                    if (stats1.getTeam() == null) {
                        if (stats2.getTeam() == null) {
                            EventTeam team = new EventTeam(name, p1.getUniqueId().toString(), p2.getUniqueId().toString());
                            team.save();
                            p.sendMessage(PvPEventPlugin.prefix + "Du hast Erfolgreich das Team erstellt.");
                            Utils.loadAllTeams();
                        } else {
                            p.sendMessage(PvPEventPlugin.prefix + "Der Spieler §6" + p1.getName() + " §7ist bereits in einem Team.");
                        }
                    } else {
                        p.sendMessage(PvPEventPlugin.prefix + "Der Spieler §6" + p2.getName() + " §7ist bereits in einem Team.");
                    }
                } else {
                    p.sendMessage(PvPEventPlugin.prefix + "Du bist nicht OP!");
                }
            } else {
                p.sendMessage(PvPEventPlugin.prefix + "/team create [name] [player1] [player2]");
            }
        }else if(args.length == 2){
         if(args[0].equalsIgnoreCase("delete")){
             String toClear = args[1];
             for(EventTeam all: PvPEventPlugin.teams){
                 if(all.getName().equalsIgnoreCase(toClear)){
                     all.delete();
                     p.sendMessage(PvPEventPlugin.prefix + "Succsesfully deleted the team §7" + all.getName());
                     return true;
                 }
             }
             p.sendMessage(PvPEventPlugin.prefix + "Couldn't find any team with the name §7" + toClear);
             return true;
         }
        }
        else {

            p.sendMessage(PvPEventPlugin.prefix + "/team");
            p.sendMessage(PvPEventPlugin.prefix + "/team create [name] [player1] [player2]");
            p.sendMessage(PvPEventPlugin.prefix + "/team delete [name]");
        }

        return true;
    }
}
