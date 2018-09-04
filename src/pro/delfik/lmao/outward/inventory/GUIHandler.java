package pro.delfik.lmao.outward.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUIHandler implements Listener {

	@EventHandler
	public void click(InventoryClickEvent e) {
		if (e.getRawSlot() < 0) return;
		GUI gui = GUI.get(e.getClickedInventory());
		if (gui == null) return;
		e.setCancelled(true);
		gui.click(e);
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		GUI gui = GUI.get(e.getInventory());
		if (gui != null && gui.isTemporary()) gui.remove();
	}

}
