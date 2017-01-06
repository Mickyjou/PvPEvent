package de.mickyjou.plugins.pvpevent.shop;

import org.bukkit.ChatColor;

import de.craften.plugins.mcguilib.SinglePageView;
import de.craften.plugins.mcguilib.View;

public class SkillsGroupView extends SinglePageView {

	/**
	 * Creates the SinglePageView
	 *
	 * @param previous
	 */

	public SkillsGroupView(View previous) {
		super(ChatColor.GRAY + "Skills", 27);
		addElement(new BackButton("Back", (e) -> getViewManager().showView(getViewer(), previous)));
	}

}
