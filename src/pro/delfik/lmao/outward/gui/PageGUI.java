package pro.delfik.lmao.outward.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class PageGUI<Type> extends GUI {
	
	private final String title;
	private GeneralizedGUI[] pages;
	
	public PageGUI(Loader loader, String title) {
		super(Bukkit.createInventory(null, 54, title));
		this.title = title;
	}
	
	@Override
	public void put(int slot, ItemStack i, Consumer<Player> action) {
		super.put(slot, i, action);
	}
	
	@Override
	public void put(int slot, ItemStack i, Consumer<Player> action, boolean shift) {
		super.put(slot, i, action, shift);
	}
	
	@Override
	public void click(Player p, int slot) {
		super.click(p, slot);
	}
	
	@Override
	public void shiftClick(Player p, int slot) {
		super.shiftClick(p, slot);
	}
	
	@Override
	public Action get(int slot) {
		return super.get(slot);
	}
	
	@Override
	public Inventory getInventory() {
		return super.getInventory();
	}
	
	@FunctionalInterface
	public interface Loader {
		List<ItemStack> load(int page);
	}
}
