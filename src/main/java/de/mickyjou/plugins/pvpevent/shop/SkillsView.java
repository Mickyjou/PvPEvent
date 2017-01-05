package de.mickyjou.plugins.pvpevent.shop;

import org.bukkit.entity.Player;

import de.craften.plugins.mcguilib.ViewManager;

public class SkillsView {
	private final ViewManager viewManager;
	
	public SkillsView(ViewManager viewManager) {
		this.viewManager=viewManager;
	}
	
	public void open(Player p){
		viewManager.showView(p, new SkillsGroupView(null));
	}

}
