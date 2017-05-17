package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearItemCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(PvPEventPlugin.prefix + "You are not allowed to execute this command.");
            return true;
        }
        Player p = (Player) commandSender;


        if (args.length == 1) {
            Integer itemID = null;
            boolean isNumeric;
            Material toClear;

            try {
                itemID = Integer.parseInt(args[0]);
                isNumeric = true;
            } catch (Exception e) {
                isNumeric = false;
            }

            if (isNumeric) {
                if (Material.getMaterial(Integer.valueOf(args[0])) != null) {
                    toClear = Material.getMaterial(Integer.valueOf(args[0]));
                } else {
                    p.sendMessage(PvPEventPlugin.prefix + "Could not find Item with the ID " + itemID);
                    return true;
                }
            } else {
                if (Material.getMaterial(args[0].toUpperCase()) != null) {
                    toClear = Material.getMaterial(args[0].toUpperCase());
                } else {
                    p.sendMessage(PvPEventPlugin.prefix + "Could not find Item with the name" + args[0].toUpperCase());
                    return true;
                }
            }

            clearItem(p, toClear, (byte) 0);
            try {
                p.sendMessage(PvPEventPlugin.prefix + "Succsessfully cleared all Items of the Type " + ChatColor.GOLD + toClear.toString() + ChatColor.GRAY + ".");
            } catch (Exception e) {
                p.sendMessage(e.getMessage());
            }


        } else if (args.length == 2) {
            p.sendMessage("lol");

        } else {
            p.sendMessage(PvPEventPlugin.prefix + "/clearitem ID (subID)");
        }


        return false;
    }


    public void clearItem(Player p, Material toClear, byte subID) {
        if (p.getInventory().contains(toClear)) {
            for (ItemStack all : p.getInventory().getContents()) {
                if (all != null && all.getType() != Material.AIR) {
                    if (all.getType() == toClear) {
                        if (all.getData().getData() == subID) {
                            p.getInventory().remove(all);
                        }
                    }
                }
            }
        }
    }


}
