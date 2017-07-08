package de.mickyjou.plugins.pvpevent.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtils {

    public static void setLobbyScoreboard(Player p){
        StatsGetter stats = new StatsGetter(p);
        Scoreboard board =  Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.getObjective("aaa") != null ? board.getObjective("aaa") : board.registerNewObjective("aaa", "bbb");

        obj.setDisplayName("§7PvP-Event");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore("§b ").setScore(11);

        if(stats.getCaseOpeningDate().equalsIgnoreCase("null")){
            obj.getScore("§6Case available.").setScore(10);
            obj.getScore("§7Open now!").setScore(9);
        }else{
            obj.getScore("§6Open Case in").setScore(10);
            obj.getScore(stats.getCaseOpeningDate()).setScore(9);
        }


        p.setScoreboard(board);

    }


    public static void updateScoreboard(Player player) {
        if (player.getScoreboard() != null) {
            Objective obj = player.getScoreboard().getObjective("aaa") != null ? player.getScoreboard().getObjective("aaa") : player.getScoreboard().registerNewObjective("aaa", "bbb");
            //TODO
        }
    }
}
