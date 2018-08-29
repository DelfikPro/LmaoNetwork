package pro.delfik.lmao.outward.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GeneralizedGUI extends GUI {
	
	public final GeneralizedAction action;
	public final GeneralizedAction shift;
	
	public GeneralizedGUI(Inventory inv, BiConsumer<Player, Integer> action, BiConsumer<Player, Integer> shift) {
		super(inv);
		this.action = new GeneralizedAction(action);
		this.shift = new GeneralizedAction(shift);
	}
	
	@Override
	public void put(int slot, ItemStack i, Consumer<Player> action) {
		getInventory().setItem(slot, i);
	}
	
	@Override
	public void put(int slot, ItemStack i, Consumer<Player> action, boolean shift) {
		getInventory().setItem(slot, i);
	}
	
	@Override
	public void click(Player p, int slot) {
		action.c.accept(p, slot);
	}
	
	@Override
	public void shiftClick(Player p, int slot) {
		shift.c.accept(p, slot);
	}
	
	@Override
	public GUI.Action get(int slot) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Inventory getInventory() {
		return super.getInventory();
	}
	
	public static class GeneralizedAction {
		public final BiConsumer<Player, Integer> c;
		
		public GeneralizedAction(BiConsumer<Player, Integer> action) {
			this.c = action;
		}
	}
}
