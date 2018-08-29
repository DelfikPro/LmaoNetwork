package pro.delfik.lmao.outward.gui;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdvancedGUI extends GUI {
	private final EventProcessor processor;
	
	public AdvancedGUI(String title, int rows, EventProcessor processor) {
		super(Bukkit.createInventory(null, rows * 9, title));
		this.processor = processor;
	}
	
	public void process(InventoryClickEvent e) {
		processor.processEvent(e);
	}
	
	
	
	@FunctionalInterface
	public interface EventProcessor {
		void processEvent(InventoryClickEvent event);
	}
}
