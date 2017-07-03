package de.mickyjou.plugins.pvpevent.listener.player;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class SignInteractListener implements Listener {

    public static List<EventTeam> teams = Utils.getAllTeams();
    public static Integer currentTeam = 0;
    private static Location firstNPC = Utils.getLocation("npcfirst");
    private static Location secondNPC = Utils.getLocation("npcsecond");
    private static Location statsLoc = Utils.getLocation("npcstats");
    private static NPC currentNPC1;
    private static NPC currentNPC2;
    private static Hologram currentHologram;
    boolean cooldown = false;


    @EventHandler
    public void onSignInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                Sign s = (Sign) e.getClickedBlock().getState();


                if (s.getLine(1).equalsIgnoreCase("Last Team")) {
                    if (cooldown == false) {
                        setLastTeam();
                        startCooldown(30);
                        cooldown = true;
                    }
                } else if (s.getLine(1).equalsIgnoreCase("Next Team")) {
                    if (cooldown == false) {
                        setNextTeam();
                        startCooldown(30);
                        cooldown = true;
                    }
                } else if (s.getLine(1).equalsIgnoreCase("Select a Team")) {
                    if (cooldown == false) {
                     //TODO   openTeamInventory();
                        startCooldown(30);
                        cooldown = true;
                    }
                }


            }
        }
    }


    public static void setFirstTeam() {
        currentTeam = 0;
        EventTeam toSet = teams.get(currentTeam);
        currentNPC1 = new NPC(toSet.getPlayers()[0].getUniqueId(), firstNPC);
        currentNPC2 = new NPC(toSet.getPlayers()[1].getUniqueId(), secondNPC);


        currentNPC1.spawn();
        currentNPC2.spawn();

        setStatsHologram(toSet);




    }


    public void setNextTeam() {
        try {
            setTeam(currentTeam + 1);
            currentTeam++;

        } catch (IndexOutOfBoundsException e) {
            setFirstTeam();
            currentTeam = 0;
        }
    }

    public void setLastTeam() {


        try {
            setTeam(currentTeam - 1);
            currentTeam--;
        } catch (IndexOutOfBoundsException e) {
            setTeam(teams.size() - 1);
            currentTeam = teams.size() - 1;
        }
    }

    public void setTeam(Integer team) {

        if (currentNPC1 != null) {
            currentNPC1.destroy();
            currentNPC2.destroy();
        }

        EventTeam toSet = teams.get(team);
        currentNPC1 = new NPC(toSet.getPlayers()[0].getUniqueId(), firstNPC);
        currentNPC2 = new NPC(toSet.getPlayers()[1].getUniqueId(), secondNPC);

        currentNPC1.spawn();
        currentNPC2.spawn();

        setStatsHologram(toSet);

    }


    public void startCooldown(int ticks) {

        new BukkitRunnable() {
            @Override
            public void run() {
                cooldown = false;
                cancel();
            }
        }.runTaskLater(PvPEventPlugin.getPlugin(PvPEventPlugin.class), ticks);
    }


    private static void setStatsHologram(EventTeam team) {
        StatsGetter stats1 = new StatsGetter(team.getPlayers()[0]);
        StatsGetter stats2 = new StatsGetter(team.getPlayers()[1]);

        String teamname = "§6Stats of the Team " + ChatColor.UNDERLINE + "§7" + team.getName();
        String kills = "§6Kills: §7" + (stats1.getKills() + stats2.getKills());
        String player1 = "§6Player 1: §7" + team.getPlayers()[0].getName();
        String player2 = "§6Player 2: §7" + team.getPlayers()[1].getName();

        currentHologram = new Hologram(new String[]{teamname,kills,player1,player2}, statsLoc.add(0,1,0));
        currentHologram.showAll();
    }
}
