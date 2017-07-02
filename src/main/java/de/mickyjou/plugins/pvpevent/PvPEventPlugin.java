package de.mickyjou.plugins.pvpevent;

import de.craften.plugins.playerdatastore.api.PlayerDataStoreService;
import de.mickyjou.plugins.pvpevent.commands.*;
import de.mickyjou.plugins.pvpevent.listener.BlockBreakListener;
import de.mickyjou.plugins.pvpevent.listener.chestopening.ChestProtectionListener;
import de.mickyjou.plugins.pvpevent.listener.chestopening.PlayerInventoryOpenListener;
import de.mickyjou.plugins.pvpevent.listener.join.PlayerJoinListener;
import de.mickyjou.plugins.pvpevent.listener.join.PlayerJoinLobbyListener;
import de.mickyjou.plugins.pvpevent.listener.join.PlayerJoinSurvivalListener;
import de.mickyjou.plugins.pvpevent.listener.player.PlayerChatListener;
import de.mickyjou.plugins.pvpevent.listener.player.PlayerInteractCompassListener;
import de.mickyjou.plugins.pvpevent.listener.player.PlayerInteractZombieListener;
import de.mickyjou.plugins.pvpevent.listener.protection.LobbyProtectionListener;
import de.mickyjou.plugins.pvpevent.listener.protection.PlayerInteractListener;
import de.mickyjou.plugins.pvpevent.listener.protection.PortalCreateListener;
import de.mickyjou.plugins.pvpevent.listener.quit.PlayerQuitListener;
import de.mickyjou.plugins.pvpevent.listener.quit.PlayerQuitLobbyListener;
import de.mickyjou.plugins.pvpevent.listener.quit.PlayerQuitSurvivalListener;
import de.mickyjou.plugins.pvpevent.listener.survival.PlayerDeathListener;
import de.mickyjou.plugins.pvpevent.listener.survival.PlayerItemConsumeListener;
import de.mickyjou.plugins.pvpevent.listener.survival.PlayerMoveListener;
import de.mickyjou.plugins.pvpevent.utils.Countdown;
import de.mickyjou.plugins.pvpevent.utils.EventTeam;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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
        Utils.loadAllTeams();
        registerServices();
        registerCommands();
        registerEvents();
        //  resetAllStores();
        startTimer();
        registerRecipes();
        Utils.spawnZombie();
        Utils.spawnChest();

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
        Utils.despawnZombie();

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
        getCommand("sethologramm").setExecutor(new HologrammCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("clearitem").setExecutor(new ClearItemCommand());
        getCommand("setzombie").setExecutor(new ZombieCommand());
        getCommand("chest").setExecutor(new ChestCommand());
        getCommand("compass").setExecutor(new CompassCommand());
        getCommand("response").setExecutor(new ResponseCommand());

    }

    /**
     * register all Events
     */

    public void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PortalCreateListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerDeathListener(this), this);
        pm.registerEvents(new PlayerJoinListener(this), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerItemConsumeListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new PlayerJoinLobbyListener(this), this);
        pm.registerEvents(new LobbyProtectionListener(), this);
        pm.registerEvents(new PlayerJoinSurvivalListener(), this);
        pm.registerEvents(new PlayerQuitSurvivalListener(), this);
        pm.registerEvents(new PlayerInteractZombieListener(), this);
        pm.registerEvents(new PlayerInventoryOpenListener(), this);
        pm.registerEvents(new ChestProtectionListener(), this);
        pm.registerEvents(new PlayerInteractCompassListener(), this);
        pm.registerEvents(new PlayerQuitLobbyListener(), this);

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
                cfg.set("LATEST_DEATHS", "");
                cfg.save(configFile);
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
        blazeToEXPBottle.shape("AAA", "ABB", "AAA");
        blazeToEXPBottle.setIngredient('B', Material.BLAZE_POWDER);


        Bukkit.addRecipe(flowersToIron);
        Bukkit.addRecipe(slimesToGold);
        Bukkit.addRecipe(lavaAndWaterToObsidian);
        Bukkit.addRecipe(blazeToEXPBottle);
    }

    public void resetAllStores() {
        for (OfflinePlayer all : Bukkit.getOfflinePlayers()) {
            StatsGetter stats = new StatsGetter(all);
            stats.getPlayerStore().clear();
        }
    }
}
