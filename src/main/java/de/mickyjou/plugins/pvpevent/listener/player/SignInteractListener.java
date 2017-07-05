package de.mickyjou.plugins.pvpevent.listener.player;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignInteractListener implements Listener {

    public static List<EventTeam> teams = Utils.getAllTeams();
    public static Integer currentTeam = 0;
    private static Location firstNPC = Utils.getLocation("npcfirst");
    private static Location secondNPC = Utils.getLocation("npcsecond");
    private static Location statsLoc = Utils.getLocation("npcstats");
    private static NPC currentNPC1;
    private static NPC currentNPC2;
    private static Hologram currentHologram;
    public static Map<String, ItemStack> cachedSkulls = new HashMap<>();
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
                        openTeamInventory(e.getPlayer());

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

    public static void setTeam(Integer team) {

        if (currentNPC1 != null) {
            currentNPC1.destroy();
            currentNPC2.destroy();
        }

        EventTeam toSet = teams.get(team);
        currentNPC1 = new NPC(toSet.getPlayers()[0].getUniqueId(), firstNPC);
        currentNPC2 = new NPC(toSet.getPlayers()[1].getUniqueId(), secondNPC);

        currentNPC1.spawn();
        currentNPC2.spawn();

        // setStatsHologram(toSet);

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
        if (currentHologram == null) {
            currentHologram = new Hologram(new String[]{teamname, kills, player1, player2}, statsLoc);
        } else {
            //TODO rename Hologram
        }
    }

    public void openTeamInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, 36);


        for (EventTeam team : PvPEventPlugin.teams) {
            inv.addItem(cachedSkulls.get(team.getPlayers()[0].getName()));
        }

        p.openInventory(inv);
    }

    public static void cacheSkulls() {
        for (EventTeam team : PvPEventPlugin.teams) {

            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(team.getPlayers()[0].getName());
            meta.setDisplayName("§6Team " + "§7§n" + team.getName());
            skull.setItemMeta(meta);
            cachedSkulls.put(team.getPlayers()[0].getName(), skull);

        }
    }
}
