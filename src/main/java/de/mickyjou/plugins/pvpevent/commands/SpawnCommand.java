package de.mickyjou.plugins.pvpevent.commands;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PvPEventPlugin.prefix + "You have to be a Player to execute this command.");
            return true;
        }

        Player p = (Player) sender;
        if (p.isOp()) {
            if (args.length == 0) {
                Utils.saveLocation(p.getLocation(), "lobby");
                p.sendMessage(PvPEventPlugin.prefix + "Erfolgreich den Lobby-Spawnpoint gesetzt.");
            }else{
                p.sendMessage(PvPEventPlugin.prefix + "/setspawn");
            }

        } else

        {
            p.sendMessage(PvPEventPlugin.prefix + "You do not have enough permissions to execute this command.");
        }
        return false;
    }
}
