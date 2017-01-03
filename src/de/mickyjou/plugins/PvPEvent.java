package de.mickyjou.plugins;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import listener.NetherPortalCreateListener;

public class PvPEvent extends JavaPlugin {

	@Override
	public void onEnable() {
		registerCommands();
		registerEvents();
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

	public void registerCommands() {

	}

	public void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new NetherPortalCreateListener(), this);
	}

}
