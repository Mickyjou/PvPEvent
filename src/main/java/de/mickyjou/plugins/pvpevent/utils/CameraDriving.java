package de.mickyjou.plugins.pvpevent.utils;


import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.math.BigDecimal;
import java.util.*;

public class CameraDriving {
    static Plugin plugin = PvPEventPlugin.getPlugin(PvPEventPlugin.class);
    static HashSet<UUID> travelling = new HashSet<UUID>();
    static HashSet<UUID> stopping = new HashSet<UUID>();

    public static double round(double unrounded, int precision) {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, 4);
        return rounded.doubleValue();
    }

    public static void travel(Player player, List<Location> locations, int time, String FailMessage,
                              String CompletedMessage) {
        Location loc = player.getLocation();
        List<Double> diffs = new ArrayList<Double>();
        List<Integer> travelTimes = new ArrayList<Integer>();

        double totalDiff = 0.0D;

        for (int i = 0; i < locations.size() - 1; i++) {
            Location s = (Location) locations.get(i);
            Location n = (Location) locations.get(i + 1);
            double diff = CameraDriving.positionDifference(s, n);
            totalDiff += diff;
            diffs.add(Double.valueOf(diff));
        }

        for (Iterator<Double> n = diffs.iterator(); n.hasNext();) {
            double d = ((Double) n.next()).doubleValue();
            travelTimes.add(Integer.valueOf((int) (d / totalDiff * time)));
        }

        final List<Location> tps = new ArrayList<Location>();

        org.bukkit.World w = player.getWorld();

        for (int i = 0; i < locations.size() - 1; i++) {
            Location s = (Location) locations.get(i);
            Location n = (Location) locations.get(i + 1);
            int t = ((Integer) travelTimes.get(i)).intValue();

            double moveX = n.getX() - s.getX();
            double moveY = n.getY() - s.getY();
            double moveZ = n.getZ() - s.getZ();


            for (int x = 0; x < t; x++) {
                Location l = new Location(w, s.getX() + moveX / t * x, s.getY() + moveY / t * x,
                        s.getZ() + moveZ / t * x, (float) -90,
                        (float) 0.1);
                tps.add(l);
            }

        }

        try {
            player.setGameMode(GameMode.SPECTATOR);
            player.setAllowFlight(true);
            player.teleport((Location) tps.get(0));
            player.setFlying(true);
            travelling.add(player.getUniqueId());
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                private int ticks = 0;

                public void run() {
                    if (this.ticks < tps.size()) {

                        player.teleport((Location) tps.get(this.ticks));

                        if (!stopping.contains(player.getUniqueId())) {
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                                    this, 1L);
                        } else {
                            stopping.remove(player.getUniqueId());
                            travelling.remove(player.getUniqueId());
                        }

                        this.ticks += 1;
                    } else {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                            @Override
                            public void run() {
                                travelling.remove(player.getUniqueId());
                                if (CompletedMessage != null)
                                    player.sendMessage(CompletedMessage);
                                player.setGameMode(GameMode.CREATIVE);
                                player.teleport(loc);

                            }

                        }, 15*20);

                    }
                }
            });

        } catch (Exception e) {
            if (FailMessage != null)
                player.sendMessage(FailMessage);
            player.setGameMode(GameMode.CREATIVE);
        }
    }

    public static double positionDifference(Location cLoc, Location eLoc) {
        double cX = cLoc.getX();
        double cY = cLoc.getY();
        double cZ = cLoc.getZ();

        double eX = eLoc.getX();
        double eY = eLoc.getY();
        double eZ = eLoc.getZ();

        double dX = eX - cX;
        if (dX < 0.0D) {
            dX = -dX;
        }
        double dZ = eZ - cZ;
        if (dZ < 0.0D) {
            dZ = -dZ;
        }
        double dXZ = Math.hypot(dX, dZ);

        double dY = eY - cY;
        if (dY < 0.0D) {
            dY = -dY;
        }
        double dXYZ = Math.hypot(dXZ, dY);

        return dXYZ;
    }

    public static boolean isTravelling(UUID PlayerUUID) {
        if (travelling.contains(PlayerUUID))
            return true;
        return false;
    }

    public static void stop(UUID PlayerUUID) {
        stopping.add(PlayerUUID);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                stopping.remove(PlayerUUID);
            }
        }, 2L);
    }

}