package de.mickyjou.plugins.pvpevent.utils;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Utils {

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


    public static String inventoryToString (Inventory invInventory)
    {
        String serialization = invInventory.getSize() + ";";
        for (int i = 0; i < invInventory.getSize(); i++)
        {
            ItemStack is = invInventory.getItem(i);
            if (is != null)
            {
                String serializedItemStack = new String();

                String isType = String.valueOf(is.getType().getId());
                serializedItemStack += "t@" + isType;

                if (is.getDurability() != 0)
                {
                    String isDurability = String.valueOf(is.getDurability());
                    serializedItemStack += ":d@" + isDurability;
                }

                if (is.getAmount() != 1)
                {
                    String isAmount = String.valueOf(is.getAmount());
                    serializedItemStack += ":a@" + isAmount;
                }

                Map<Enchantment,Integer> isEnch = is.getEnchantments();
                if (isEnch.size() > 0)
                {
                    for (Map.Entry<Enchantment,Integer> ench : isEnch.entrySet())
                    {
                        serializedItemStack += ":e@" + ench.getKey().getId() + "@" + ench.getValue();
                    }
                }

                serialization += i + "#" + serializedItemStack + ";";
            }
        }
        return serialization;
    }

    public static Inventory stringToInventory (String invString)
    {
        String[] serializedBlocks = invString.split(";");
        String invInfo = serializedBlocks[0];
        Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo));

        for (int i = 1; i < serializedBlocks.length; i++)
        {
            String[] serializedBlock = serializedBlocks[i].split("#");
            int stackPosition = Integer.valueOf(serializedBlock[0]);

            if (stackPosition >= deserializedInventory.getSize())
            {
                continue;
            }

            ItemStack is = null;
            Boolean createdItemStack = false;

            String[] serializedItemStack = serializedBlock[1].split(":");
            for (String itemInfo : serializedItemStack)
            {
                String[] itemAttribute = itemInfo.split("@");
                if (itemAttribute[0].equals("t"))
                {
                    is = new ItemStack(Material.getMaterial(Integer.valueOf(itemAttribute[1])));
                    createdItemStack = true;
                }
                else if (itemAttribute[0].equals("d") && createdItemStack)
                {
                    is.setDurability(Short.valueOf(itemAttribute[1]));
                }
                else if (itemAttribute[0].equals("a") && createdItemStack)
                {
                    is.setAmount(Integer.valueOf(itemAttribute[1]));
                }
                else if (itemAttribute[0].equals("e") && createdItemStack)
                {
                    is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1])), Integer.valueOf(itemAttribute[2]));
                }
            }
            deserializedInventory.setItem(stackPosition, is);
        }

        return deserializedInventory;
    }

}
