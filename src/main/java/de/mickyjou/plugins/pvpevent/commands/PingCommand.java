package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PvPEventPlugin.prefix + "You have to be a Player to execute this command.");
            return true;
        }
        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage(PvPEventPlugin.prefix + "Your Ping is §6" + getPing(p) + "ms");
        } else {
            p.sendMessage(PvPEventPlugin.prefix + "/ping");
        }
        return false;
    }

    private Integer getPing(Player p) {
        CraftPlayer craftPlayer = (CraftPlayer) p;
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        return entityPlayer.ping;
    }
}
