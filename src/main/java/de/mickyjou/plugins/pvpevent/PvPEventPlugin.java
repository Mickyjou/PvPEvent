package de.mickyjou.plugins.pvpevent;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.mickyjou.plugins.pvpevent.commands.SchematicCommands;
import de.mickyjou.plugins.pvpevent.commands.SkillsCommand;
import de.craften.plugins.mcguilib.ViewManager;
import de.mickyjou.plugins.pvpevent.listener.PlayerInteractListener;
import de.mickyjou.plugins.pvpevent.listener.PlayerMoveListener;
import de.mickyjou.plugins.pvpevent.listener.PortalCreateListener;
import de.mickyjou.plugins.pvpevent.shop.SkillsView;
import de.mickyjou.plugins.pvpevent.utils.SchematicManager;

public class PvPEventPlugin extends JavaPlugin {

	SchematicManager sm;
	public static String prefix = ChatColor.GOLD + "[PvPEvent] " + ChatColor.GRAY;
	public ViewManager vm;
	public SkillsView skw;

	@Override
	public void onEnable() {
		registerCommands();
		registerEvents();
		sm = new SchematicManager(this);
		loadFiles();
		vm = new ViewManager(this);
		skw = new SkillsView(vm);
		super.onEnable();

	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onLoad() {
		super.onLoad();
	}

	/**
	 * Register all de.mickyjou.plugins.pvpevent.commands
	 */
	public void registerCommands() {
		getCommand("schematic").setExecutor(new SchematicCommands(this));
		getCommand("skills").setExecutor(new SkillsCommand(this));

	}

	/**
	 * register all Events
	 */

	public void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PortalCreateListener(), this);
		pm.registerEvents(new PlayerMoveListener(), this);
		pm.registerEvents(new PlayerInteractListener(this), this);
	}

	private void loadFiles() {
		File file = new File(this.getDataFolder().getAbsolutePath() + "/schematics");
		if (!file.exists())
			file.mkdirs();

	}

}
