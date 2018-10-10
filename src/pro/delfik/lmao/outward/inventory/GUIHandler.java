package pro.delfik.lmao.outward.inventory;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GUIHandler implements Listener {

	@EventHandler
	public void click(InventoryClickEvent e) {
		if (e.getRawSlot() < 0) return;
		GUI gui = GUI.get(e.getInventory());
		if (gui == null) return;
		if (!gui.listenAll) {
			if (e.getInventory() != e.getClickedInventory()) return;
			e.setCancelled(true);
			ItemStack i = e.getCurrentItem();
			if (i == null || i.getType() == Material.AIR) return;
		}
		e.setCancelled(true);
		gui.click(e);
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		GUI gui = GUI.get(e.getInventory());
		if (gui != null && gui.isTemporary()) gui.remove();
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() != Action.RIGHT_CLICK_AIR)) return;
		GUI gui = GUI.assigned.get(e.getMaterial());
		if (gui == null) return;
		e.setCancelled(true);
		e.getPlayer().openInventory(gui.inv());
	}

}
