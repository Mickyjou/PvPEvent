package de.mickyjou.plugins.pvpevent.utils;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

;

/**
 * Created by Mickyjou on 15.01.2017.
 */
public class Countdown {

    private static HashMap<Player, Integer> times = new HashMap<>();
    private static HashMap<Player, Boolean> stopped = new HashMap<>();

    private static void startCouuntdown(Player p) {
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
                p.sendMessage(time + "");

                switch (time) {
                    case 60:
                    case 30:
                    case 15:
                    case 10:
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                    case 1:
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" +
                                p.getName() + " §7wird in " + time + " Sekunden gekickt.");
                        break;
                    case 0:
                        p.kickPlayer(PvPEventPlugin.prefix + "Deine Spielzeit ist vorbei!");
                        Bukkit.broadcastMessage(PvPEventPlugin.prefix + "Der Spieler §6" + p.getName() + " §7wurde gekickt.");
                        cancel();
                        break;
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


    public static void startPreCountdown(Player p) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(PvPEventPlugin.getPlugin(PvPEventPlugin.class), new Runnable() {
            int i =31;
            @Override
            public void run() {
                if(i >= 0){

                    i--;
                    switch (i){
                        case 30:case 15:case 10:case 5:case 4:case 3:case 2:case 1: p.sendMessage(PvPEventPlugin.prefix + "Du hast noch §7" + i + "§6 Sekunden Schutzzeit"); break;
                        case 0: p.sendMessage(PvPEventPlugin.prefix + "Deine Schutzzeit ist vorbei! Du bist nun verwundbar.");  startCouuntdown(p); break;
                    }
                }

            }
        },0,20);


    }
}
