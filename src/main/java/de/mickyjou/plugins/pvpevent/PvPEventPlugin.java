package de.mickyjou.plugins.pvpevent;

import de.craften.plugins.bkcommandapi.SubCommandHandler;
import de.craften.plugins.mcguilib.ViewManager;
import de.craften.plugins.playerdatastore.api.PlayerDataStoreService;
import de.mickyjou.plugins.pvpevent.commands.*;
import de.mickyjou.plugins.pvpevent.listener.*;
import de.mickyjou.plugins.pvpevent.utils.Countdown;
import de.mickyjou.plugins.pvpevent.utils.EventTeam;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PvPEventPlugin extends JavaPlugin {

    public static String prefix = ChatColor.GOLD + "[PvPEvent] " + ChatColor.GRAY;
    public static ArrayList<EventTeam> teams;
    private static ViewManager vm;
    private static PlayerDataStoreService pds;


    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
        registerServices();
        Utils.loadAllTeams();
        loadFiles();
        vm = new ViewManager(this);
        startTimer();
        super.onEnable();

    }

    private void startTimer() {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {

                Date date = new Date();
                String[] days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
                String currentDay = days[date.getDay()];
                if(currentDay == days[1]){

                }

            }
        }, 0, 60 * (60 * 20));
    }

    @Override
    public void onDisable() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            Countdown.stopCountdown(all);
        }

        super.onDisable();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    /**
     * Register all de.mickyjou.plugins.pvpevent.commands
     */
    public void registerCommands() {

        SubCommandHandler skillsCommandHandler = new SubCommandHandler("skills") {
            @Override
            protected void onInvalidCommand(CommandSender commandSender) {
                commandSender.sendMessage(ChatColor.RED + "Unknown command.");
            }

            @Override
            protected void onPermissionDenied(CommandSender commandSender, Command command, String[] strings) {
                commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            }
        };



        skillsCommandHandler.addHandlers(new SkillsCommand(this));
        getCommand("skills").setExecutor(skillsCommandHandler);

        getCommand("warnings").setExecutor(new WarningCommand());
        getCommand("team").setExecutor(new TeamCommand());

        getCommand("teams").setExecutor(new TeamsCommand());

    }

    /**
     * register all Events
     */

    public void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PortalCreateListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerChatListener(), this);
    }

    public void registerServices() {
        pds = Bukkit.getServer().getServicesManager()
                .getRegistration(PlayerDataStoreService.class).getProvider();
    }

    public static PlayerDataStoreService getPDSService() {
        return pds;
    }

    private void loadFiles() {
        File file = new File(this.getDataFolder().getAbsolutePath() + "/schematics");
        if (!file.exists())
            file.mkdirs();

    }

    public static ViewManager getViewManager() {
        return vm;
    }


}
