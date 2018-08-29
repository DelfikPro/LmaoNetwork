package pro.delfik.lmao.outward.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.function.Consumer;

public class GUI {
	public static final HashMap<Inventory, GUI> list = new HashMap<>();
	
	protected final Inventory inv;
	private final Action[] slots;
	private final Action[] shift;
	
	public GUI(Inventory inv) {
		this.inv = inv;
		this.slots = new Action[inv.getSize()];
		this.shift = new Action[inv.getSize()];
		list.put(inv, this);
	}
	
	public void put(int slot, ItemStack i, Consumer<Player> action) {put(slot, i, action, false);}
	public void put(int slot, ItemStack i, Consumer<Player> action, boolean shift) {
		inv.setItem(slot, i);
		if (shift) this.shift[slot] = new Action(action); else slots[slot] = new Action(action);
	}
	
	public void click(Player p, int slot) {
		Action a = slots[slot];
		if (a != null) a.getC().accept(p);
	}
	
	public void shiftClick(Player p, int slot) {
		Action a = shift[slot] == null ? slots[slot] : shift[slot];
		if (a != null) a.getC().accept(p);
	}
	
	public Action get(int slot) {
		return slot < slots.length ? slots[slot] : null;
	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	public static class Action {
		private final Consumer<Player> c;
		
		public Action(Consumer<Player> c) {
			this.c = c;
		}
		
		public Consumer<Player> getC() {
			return c;
		}
	}
}
