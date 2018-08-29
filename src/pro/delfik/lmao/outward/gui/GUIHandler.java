package pro.delfik.lmao.outward.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIHandler implements Listener {
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		GUI gui = GUI.list.get(e.getInventory());
		if (gui == null) return;
		e.setCancelled(true);
		if (gui instanceof AdvancedGUI) {
			((AdvancedGUI) gui).process(e);
			return;
		}
		if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) gui.shiftClick(((Player) e.getWhoClicked()), e.getRawSlot());
		else gui.click((Player) e.getWhoClicked(), e.getRawSlot());
	}
}
