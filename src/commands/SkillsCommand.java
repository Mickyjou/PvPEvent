package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mickyjou.plugins.PvPEvent;

public class SkillsCommand implements CommandExecutor {
	private PvPEvent plugin;

	public SkillsCommand(PvPEvent plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player p = (Player) sender;
		if (args.length > 0) {
			p.sendMessage(PvPEvent.prefix + "/prefix");
			return true;
		} else {

			plugin.skw.open(p);
			return true;
		}

	}

}
