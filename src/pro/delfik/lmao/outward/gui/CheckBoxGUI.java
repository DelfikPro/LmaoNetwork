package pro.delfik.lmao.outward.gui;

import pro.delfik.lmao.outward.Generate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CheckBoxGUI extends GUI {
	
	private final static ItemStack UNCHECK = Generate.itemstack(Material.BARRIER, 1, 0, "§f>> §c§lУдалить §f<<");
	private final static ItemStack DONE = Generate.itemstack(Material.WOOL, 1, 5, "§f>> §a§lГотово §f<<");
	
	
	private LinkedList<Integer> checked = new LinkedList<>();
	private BiConsumer<Player, LinkedList<Integer>> onDone;
	
	public CheckBoxGUI(int boxes, String title, BiConsumer<Player, LinkedList<Integer>> onDone) {
		super(Bukkit.createInventory(null, 18, title));
		getInventory().setItem(17, DONE);
		this.onDone = onDone;
	}
	
	@Deprecated
	@Override
	public void put(int slot, ItemStack i, Consumer<Player> action) {
		super.put(slot, i, action);
	}
	
	@Deprecated
	@Override
	public void put(int slot, ItemStack i, Consumer<Player> action, boolean shift) {
		super.put(slot, i, action, shift);
	}
	
	public int addItem(ItemStack i) {
		int slot = getInventory().firstEmpty();
		getInventory().setItem(slot, i);
		return slot;
	}
	
	@Override
	public void shiftClick(Player p, int slot) {
		if (slot == getInventory().getSize() - 1) {
			onDone.accept(p, checked);
			return;
		}
		ItemStack item = getInventory().getItem(slot);
		if (item == null || item.getType() == Material.AIR) return;
		if (slot % 18 > 8) {
			if (item.getType() != Material.BARRIER) return;
			getInventory().setItem(slot, new ItemStack(Material.AIR));
			checked.remove(new Integer(slot));
			p.updateInventory();
		} else {
			checked.add(slot);
			getInventory().setItem(slot + 9, UNCHECK);
			p.updateInventory();
		}
	}
	
	@Override
	public void click(Player p, int slot) {
		if (checked.isEmpty()) {
			checked.add(slot);
			onDone.accept(p, checked);
			checked = new LinkedList<>();
		} else shiftClick(p, slot);
	}
	
	@Deprecated
	@Override
	public Action get(int slot) {
		return super.get(slot);
	}
	
}
