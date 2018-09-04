package pro.delfik.lmao.outward.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CommonGUI extends GUI {

	private final Action[] slots, shift;

	public CommonGUI(String title, int size, boolean temporary) {
		super(create(size * 9, title), temporary);
		this.slots = new Action[inv().getSize()];
		this.shift = new Action[inv().getSize()];
	}

	public void put(int slot, ItemStack i, Action action) {put(slot, i, action, false);}
	public void put(int slot, ItemStack i, Action action, boolean shift) {
		inv().setItem(slot, i);
		if (shift) this.shift[slot] = action; else slots[slot] = action;
	}

	@Override
	public void click(InventoryClickEvent event) {
		if (event.getRawSlot() > slots.length) return;
		Player p = (Player) event.getWhoClicked();
		int slot = event.getRawSlot();
		if (event.isShiftClick()) shiftClick(p, slot);
		else click(p, slot);
	}
	public void click(Player p, int slot) {
		Action a = slots[slot];
		if (a != null) a.accept(p);
	}

	public void shiftClick(Player p, int slot) {
		Action a = shift[slot] == null ? slots[slot] : shift[slot];
		if (a != null) a.accept(p);
	}

	public Action get(int slot) {
		return (slot < slots.length && slot > -1) ? slots[slot] : null;
	}

	@FunctionalInterface
	public interface Action {
		void accept(Player p);
	}


}
