package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.mickyjou.plugins.PvPEvent;
import utils.SchematicManager;

public class SchematicCommands implements CommandExecutor {

	private Player p;
	public PvPEvent plugin;
	public static Map<Player, Location> loc1 = new HashMap<>();
	public static Map<Player, Location> loc2 = new HashMap<>();

	SchematicManager sm = new SchematicManager(plugin);

	public SchematicCommands(PvPEvent plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		this.p = (Player) sender;
		if (args.length == 0) {
			sendHelp();
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				sendHelp();
			} else {
				sendHelp();
			}
		}

		if (args.length == 2) {
			String name = args[1];
			if (args[0].equalsIgnoreCase("save")) {
				if (loc1.get(p) != null && loc2.get(p) != null) {
					ArrayList<String> list = sm.blockListConverter(sm.getBlocks(loc1.get(p), loc2.get(p)), loc1.get(p));
					if (sm.save(name, list)) {
						p.sendMessage(PvPEvent.prefix + "Schematic erfolgreich gespeichert");
					} else {
						p.sendMessage(PvPEvent.prefix + "Fehler beim Speichern der Schematic");
					}
				}else{
					p.sendMessage(PvPEvent.prefix + "Bitte wähle zwei Locations aus!");
				}
			}
			if (args[0].equalsIgnoreCase("paste")) {
				if (sm.paste(name, p.getLocation().add(5, 0, 0))) {
					p.sendMessage(PvPEvent.prefix + "Schematic erfolgreich eingefügt");
				} else {
					p.sendMessage(PvPEvent.prefix + "Fehler beim Einfügen der Schematic");
				}

			}
			if (args[0].equalsIgnoreCase("delete")) {
				if (sm.delete(name)) {
					p.sendMessage(PvPEvent.prefix + "Schematic erfolgreich gelöscht");
				}else{
					p.sendMessage(PvPEvent.prefix + "Die Schematic existiert nicht");
				}
			}
			if (args[0].equalsIgnoreCase("load")) {
				if (sm.load(name)) {

					p.sendMessage(PvPEvent.prefix + "Schematic erfolgreich geladen");
				}else{
					p.sendMessage(PvPEvent.prefix + "Fehler beim Laden");
				}

			}
		}
		return false;
	}

	public void sendHelp() {
		p.sendMessage(PvPEvent.prefix + " /schematics help");
		p.sendMessage(PvPEvent.prefix + " /schematics delete [name]");
		p.sendMessage(PvPEvent.prefix + " /schematics save [name]");
		p.sendMessage(PvPEvent.prefix + " /schematics load [name]");
		p.sendMessage(PvPEvent.prefix + " /schematics paste [name]");

	}
}
