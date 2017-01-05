package de.mickyjou.plugins;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import listener.PlayerMoveListener;
import listener.PortalCreateListener;
import utils.SchematicManager;

public class PvPEvent extends JavaPlugin {

	SchematicManager sm;

	@Override
	public void onEnable() {
		registerCommands();
		registerEvents();
		sm = new SchematicManager(this);

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

	}

	/**
	 * register all Events
	 */

	public void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PortalCreateListener(), this);
		pm.registerEvents(new PlayerMoveListener(), this);
	}

}
