package de.mickyjou.plugins.pvpevent.utils;

;
import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

/**
 * Created by Mickyjou on 15.01.2017.
 */
public class Countdown {

    private static HashMap<Player, Integer> times = new HashMap<>();
    private static HashMap<Player, Boolean> stopped = new HashMap<>();

    public static void startCouuntdown(Player p) {
        StatsGetter getter = new StatsGetter(p);
        if (Integer.valueOf(getter.getPlayerStore().get("playtime")) == 0) {
            getter.getPlayerStore(
            ).put("playtime", String.valueOf(getMaxPlayTime()));
        }
        stopped.put(p, false);

        new BukkitRunnable() {

            int time = Integer.valueOf(getter.getPlayerStore().get("playtime"));

            @Override
            public void run() {
                if (stopped.get(p) == true) cancel();
                time--;
                times.put(p, time);

                switch (time) {
                    case 60:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                    case 30:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                    case 15:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                    case 10:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                    case 5:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                    case 4:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                    case 3:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                    case 2:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                    case 1:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                    case 0:
                        p.kickPlayer(PvPEventPlugin.prefix + "Deine Spielzeit ist vorbei!");
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" + p.getName() + " §7wurde gekickt.");
                }


            }

        }.runTaskTimer(PvPEventPlugin.getPlugin(PvPEventPlugin.class), 0, 20);


    }

    public static void stopCountdown(Player p) {
        StatsGetter getter = new StatsGetter(p);
        if (times.containsKey(p)) {
            stopped.put(p, true);
            getter.getPlayerStore().put("playtime", String.valueOf(times.get(p)));
        }
    }


    private static int getMaxPlayTime() {
        return 15 * 60;
    }


}
