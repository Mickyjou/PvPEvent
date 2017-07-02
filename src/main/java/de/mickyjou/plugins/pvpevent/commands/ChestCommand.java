package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChestCommand implements CommandExecutor {

    public static List<Player> players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You've to be a Player to execute this command.");
            return true;
        }



        Player p = (Player) commandSender;
        if(!p.isOp()) {
            p.sendMessage(PvPEventPlugin.prefix + "You've to be OP to execute this command.");
            return true;
        }
        if (args.length == 0) {
            sendHelp(p);
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("setlocation")) {
                players.add(p);
                p.sendMessage(PvPEventPlugin.prefix + "Klicke auf einen Block um die Position zu setzen.");

            } else if (args[0].equalsIgnoreCase("clearlist")) {
                Utils.clearChestList();
                p.sendMessage(PvPEventPlugin.prefix + "Succesfully cleared the itemlist");
            } else {
                sendHelp(p);
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("additem")) {
                String mat = args[1].toUpperCase();
                String amn = args[2];
                Material material;
                Integer amount;

                if (Material.getMaterial(mat) != null) {
                    material = Material.valueOf(mat);
                    try {
                        amount = Integer.parseInt(amn);
                    } catch (Exception e) {

                        p.sendMessage(PvPEventPlugin.prefix + amn + " is no valid number.");
                        return true;
                    }
                } else {
                    p.sendMessage(PvPEventPlugin.prefix + "Invalid Material.");
                    return true;
                }

                Utils.addChestItem(material, amount);
                p.sendMessage(PvPEventPlugin.prefix + "Succesfully added item.");

            }
        } else {
            sendHelp(p);
        }
        return false;
    }


    public void sendHelp(Player p) {
        p.sendMessage("/chest setlocation");
        p.sendMessage("/chest additem <Material> <Amount>");
        p.sendMessage("/chest clearlist");
    }
}
