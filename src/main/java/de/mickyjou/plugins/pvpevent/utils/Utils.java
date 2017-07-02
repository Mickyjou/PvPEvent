package de.mickyjou.plugins.pvpevent.utils;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftChest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Chest;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static ArmorStand zombie;

    private static Plugin plugin = PvPEventPlugin.getPlugin(PvPEventPlugin.class);

    /**
     * Creates a new World
     *
     * @param name
     */

    public static void createNewWorld(String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.type(WorldType.NORMAL);
        wc.generateStructures(true);
        wc.createWorld();
    }


    public static void loadAllTeams() {
        File teamsfile = new File(FileManager.getDataFolder(), "Teams.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(teamsfile);
        ArrayList<EventTeam> teams = new ArrayList<>();

        for (String all : cfg.getKeys(false)) {
            ConfigurationSection section = cfg.getConfigurationSection(all);
            teams.add(new EventTeam(all, section.getString("player1"), section.getString("player2")));
        }

        PvPEventPlugin.teams = teams;
    }

    public static ArrayList<EventTeam> getAllTeams() {
        return PvPEventPlugin.teams;
    }

    public static void saveLocation(Location location, String name) {
        name = name + ".";


        PvPEventPlugin.cfg.set(name + "world", location.getWorld().getName());
        PvPEventPlugin.cfg.set(name + "x", location.getX());
        PvPEventPlugin.cfg.set(name + "y", location.getY());
        PvPEventPlugin.cfg.set(name + "z", location.getZ());
        PvPEventPlugin.cfg.set(name + "yaw", location.getYaw());
        PvPEventPlugin.cfg.set(name + "pitch", location.getPitch());

        try {
            PvPEventPlugin.cfg.save(PvPEventPlugin.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static Location getLocation(String name) {
        name = name + ".";

        String world = PvPEventPlugin.cfg.getString(name + "world");
        Double x = PvPEventPlugin.cfg.getDouble(name + "x");
        Double y = PvPEventPlugin.cfg.getDouble(name + "y");
        Double z = PvPEventPlugin.cfg.getDouble(name + "z");
        float yaw = (float) PvPEventPlugin.cfg.getDouble(name + "yaw");
        float pitch = (float) PvPEventPlugin.cfg.getDouble(name + "pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }


    public static void spawnZombie() {
        Location loc = Utils.getLocation("zombie");
        if (loc != null) {
            try {
                zombie = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                zombie.setVisible(true);
                zombie.setArms(true);
                zombie.setCustomName("§6JOIN SURVIVAL");
                zombie.setCustomNameVisible(true);
                zombie.setHelmet(new ItemStack(Material.getMaterial(397), 1, (byte) 2));
                zombie.setBoots(getGreenArmor(0));
                zombie.setLeggings(getGreenArmor(1));
                zombie.setChestplate(getGreenArmor(2));
                zombie.setBasePlate(false);
                zombie.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
            } catch (Exception e) {
            }

        }

    }

    public static void despawnZombie() {
        if (zombie != null) zombie.remove();
    }


    public static ItemStack getGreenArmor(int id) {
        ItemStack stack;
        if (id == 0) {
            stack = new ItemStack(Material.LEATHER_BOOTS);
        } else if (id == 1) {
            stack = new ItemStack(Material.LEATHER_LEGGINGS);
        } else if (id == 2) {
            stack = new ItemStack(Material.LEATHER_CHESTPLATE);
        } else if (id == 3) {
            stack = new ItemStack(Material.LEATHER_HELMET);
        } else return null;

        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(Color.GREEN);
        stack.setItemMeta(meta);
        return stack;
    }

    public static String getStringLocation(final Location l) {
        if (l == null) {
            return "";
        }
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }


    public static Location getLocationString(final String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }


    public static void spawnChest() {

        Location loc = getLocation("chest");
        if (loc == null) return;
        Block block = loc.getBlock();
        block.setType(Material.CHEST);
        BlockState state = block.getState();
        Chest chest = new Chest(BlockFace.EAST);
        state.setData(chest);
        state.update();

        CraftChest craftchest = (CraftChest) loc.getBlock().getState();
        craftchest.getTileEntity().a("§6Daily Reward");


    }


    public static void addChestItem(Material material, int amount) {
        File file = new File(FileManager.getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        List<String> items = cfg.getStringList("Chestitems");
        items.add(material.name() + " " + String.valueOf(amount));
        cfg.set("Chestitems", items);


        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void clearChestList() {
        File file = new File(FileManager.getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        List<String> items = cfg.getStringList("Chestitems");
        items.clear();
        cfg.set("Chestitems", items);

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<ItemStack> getChestItems() {
        File file = new File(FileManager.getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);


        List<ItemStack> items = new ArrayList<>();
        List<String> toSplit = cfg.getStringList("Chestitems");
        for(String all: toSplit){
            String[] split = all.split(" ");
            items.add(new ItemStack(Material.valueOf(split[0]), Integer.parseInt(split[1])));
        }

        return items;
    }

    public static void removeLastChestItem() {
        File file = new File(FileManager.getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        List<String> items = cfg.getStringList("Chestitems");
        items.remove(items.size() - 1);
        cfg.set("Chestitems", items);

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
