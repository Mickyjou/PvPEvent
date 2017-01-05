package de.mickyjou.plugins.pvpevent.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;

public class SkillsCommand implements CommandExecutor {
	private PvPEventPlugin plugin;

	public SkillsCommand(PvPEventPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player p = (Player) sender;
		if (args.length > 0) {
			p.sendMessage(PvPEventPlugin.prefix + "/prefix");
			return true;
		} else {

			plugin.skw.open(p);
			return true;
		}

	}

}
