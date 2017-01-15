package de.mickyjou.plugins.pvpevent.utils;

;
import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

/**
 * Created by Mickyjou on 15.01.2017.
 */
public class Countdown {

    private static HashMap<Player, Integer> times = new HashMap<>();
    private static HashMap<Player, BukkitTask> tasks = new HashMap<>();

    public static void startCouuntdown(Player p) {
        Utils.getPlayerStore(p).put("playtime", String.valueOf(getMaxPlayTime()));


        BukkitTask runnable = new BukkitRunnable() {
            int time = Integer.valueOf(Utils.getPlayerStore(p).get("playtime"));

            @Override
            public void run() {
                time--;
                times.put(p, time);
            }
        }.runTaskTimer(PvPEventPlugin.getPlugin(PvPEventPlugin.class), 0, 20);

        tasks.put(p, runnable);
    }

    public static void stopCountdown(Player p) {
        if (tasks.containsKey(p)) {
            BukkitTask task = tasks.get(p);
            task.cancel();
        }
    }


    private static int getMaxPlayTime() {
        return 15 * (60 * 20);
    }


}
