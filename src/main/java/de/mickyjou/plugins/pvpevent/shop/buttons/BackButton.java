package de.mickyjou.plugins.pvpevent.shop.buttons;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.craften.plugins.mcguilib.Button;
import de.craften.plugins.mcguilib.ClickListener;

public class BackButton extends Button {

	/**
	 * Create a Button used as BackButton
	 *
	 * @param title
	 * @param onClick
	 */

	public BackButton(String title, ClickListener onClick) {
		super(Material.SKULL_ITEM, (byte) 3, title);
		setOnClick(onClick);
	}

	@Override
	public ItemStack createItem() {
		ItemStack item = super.createItem();
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner("MHF_ArrowLeft");
		item.setItemMeta(meta);
		return item;
	}

}