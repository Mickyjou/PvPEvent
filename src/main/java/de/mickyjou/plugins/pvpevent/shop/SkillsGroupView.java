package de.mickyjou.plugins.pvpevent.shop;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.shop.buttons.CombatButton;
import org.bukkit.ChatColor;

import de.craften.plugins.mcguilib.SinglePageView;
import org.bukkit.entity.Player;

import javax.sound.midi.SysexMessage;

public class SkillsGroupView extends SinglePageView {

    /**
     * Creates the SinglePageView
     *
     */

    public SkillsGroupView() {
        super(ChatColor.GRAY + "Skills", 27);
        Player p = getViewer();
        addElement(new CombatButton(p, (e) -> PvPEventPlugin.getSkillsView().openCombatMenu(p)));

    }

}
