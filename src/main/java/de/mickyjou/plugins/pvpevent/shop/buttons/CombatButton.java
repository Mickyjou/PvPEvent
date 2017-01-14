package de.mickyjou.plugins.pvpevent.shop.buttons;

import de.craften.plugins.mcguilib.Button;
import de.craften.plugins.mcguilib.ClickListener;
import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Michi on 07.01.2017.
 */
public class CombatButton extends Button {

    public CombatButton( Player viewer, ClickListener onClick) {
        super(Material.DIAMOND_AXE, "§7Combat", "§7Öffne das Combat-Menu");
        setOnClick(onClick);
    }

}
