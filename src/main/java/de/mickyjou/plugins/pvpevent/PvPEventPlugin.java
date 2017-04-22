package de.mickyjou.plugins.pvpevent;

import de.craften.plugins.playerdatastore.api.PlayerDataStoreService;
import de.mickyjou.plugins.pvpevent.commands.SpawnCommand;
import de.mickyjou.plugins.pvpevent.commands.TeamCommand;
import de.mickyjou.plugins.pvpevent.commands.TeamsCommand;
import de.mickyjou.plugins.pvpevent.commands.WarningCommand;
import de.mickyjou.plugins.pvpevent.listener.*;
import de.mickyjou.plugins.pvpevent.utils.Countdown;
import de.mickyjou.plugins.pvpevent.utils.EventTeam;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class PvPEventPlugin extends JavaPlugin {

    public static String prefix = ChatColor.GOLD + "[PvPEvent] " + ChatColor.GRAY;
    public static ArrayList<EventTeam> teams;
    private static PlayerDataStoreService pds;
    public static File configFile;
    public static FileConfiguration cfg;


    @Override
    public void onEnable() {

        loadFiles();
        registerEvents();
        registerServices();
        // resetAllStores();
        Utils.loadAllTeams();
        startTimer();
        registerCommands();
        registerRecipes();
        super.onEnable();
    }

    private void startTimer() {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {

                Date date = new Date();
                String[] days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
                String currentDay = days[date.getDay()];
                if (currentDay == days[1]) {

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


        getCommand("warnings").setExecutor(new WarningCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("teams").setExecutor(new TeamsCommand());
        getCommand("setspawn").setExecutor(new SpawnCommand());

    }

    /**
     * register all Events
     */

    public void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PortalCreateListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerJoinListener(this), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerItemConsumeListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
    }

    public void registerServices() {
        pds = Bukkit.getServer().getServicesManager()
                .getRegistration(PlayerDataStoreService.class).getProvider();
    }

    public static PlayerDataStoreService getPDSService() {
        return pds;
    }

    private void loadFiles() {
        configFile = new File(getDataFolder(), "config.yml");
        cfg = YamlConfiguration.loadConfiguration(configFile);

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


    public void registerRecipes() {
        ShapedRecipe flowersToIron = new ShapedRecipe(new ItemStack(Material.IRON_INGOT));
        flowersToIron.shape("FFF", "FFF", "FFF");
        flowersToIron.setIngredient('F', Material.RED_ROSE);

        ShapedRecipe slimesToGold = new ShapedRecipe(new ItemStack(Material.GOLD_INGOT));
        slimesToGold.shape("AAA", "ASS", "AAA");
        slimesToGold.setIngredient('S', Material.SLIME_BALL);

        ShapedRecipe lavaAndWaterToObsidian = new ShapedRecipe(new ItemStack(Material.OBSIDIAN));
        lavaAndWaterToObsidian.shape("AAA", "AWL", "AAA");
        lavaAndWaterToObsidian.setIngredient('W', Material.WATER_BUCKET);
        lavaAndWaterToObsidian.setIngredient('L', Material.LAVA_BUCKET);

        ShapedRecipe blazeToEXPBottle = new ShapedRecipe(new ItemStack(Material.EXP_BOTTLE));
        blazeToEXPBottle.shape("AAA","ABB","AAA");
        blazeToEXPBottle.setIngredient('B', Material.BLAZE_POWDER);


        Bukkit.addRecipe(flowersToIron);
        Bukkit.addRecipe(slimesToGold);
        Bukkit.addRecipe(lavaAndWaterToObsidian);
        Bukkit.addRecipe(blazeToEXPBottle);
    }


}
