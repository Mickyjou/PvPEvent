package de.mickyjou.plugins.pvpevent.commands;

import de.craften.plugins.bkcommandapi.CommandHandler;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;

import java.util.UUID;

public class SkillsCommand implements CommandHandler {
	private PvPEventPlugin plugin;

	public SkillsCommand(PvPEventPlugin plugin) {
		this.plugin = plugin;
	}


	@de.craften.plugins.bkcommandapi.Command(
		max = 0,
			allowFromConsole = false,
			description = "Open the Skills-Menu",
			usage = "skills"
	)
	public void openSkillsMenu(Player p){
	//	PvPEventPlugin.getSkillsView().openSkillsMenu(p);
	}

}
