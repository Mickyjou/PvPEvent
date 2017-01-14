package de.mickyjou.plugins.pvpevent.shop;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.shop.buttons.CombatButton;
import org.bukkit.ChatColor;

import de.craften.plugins.mcguilib.SinglePageView;
import org.bukkit.entity.Player;

public class SkillsGroupView extends SinglePageView {

    /**
     * Creates the SinglePageView
     *
     */

    public SkillsGroupView(Player p) {
        super(ChatColor.GRAY + "Skills", 27);
        insertElement(10,new CombatButton(p, (e) -> PvPEventPlugin.getSkillsView().openCombatMenu(p)));


    }

}
