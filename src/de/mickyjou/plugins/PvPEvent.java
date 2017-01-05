package de.mickyjou.plugins;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import commands.SchematicCommands;
import commands.SkillsCommand;
import de.craften.plugins.mcguilib.ViewManager;
import listener.PlayerInteractListener;
import listener.PlayerMoveListener;
import listener.PortalCreateListener;
import shop.SkillsView;
import utils.SchematicManager;

public class PvPEvent extends JavaPlugin {

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
	 * Register all commands
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
