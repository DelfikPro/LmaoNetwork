package pro.delfik.lmao.outward.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.outward.Generate;

import java.util.LinkedList;
import java.util.function.BiConsumer;

public class CheckBoxGUI extends GUI {

	private final static ItemStack UNCHECK = Generate.itemstack(Material.BARRIER, 1, 0, "§f>> §c§lУдалить §f<<");
	private final static ItemStack DONE = Generate.itemstack(Material.WOOL, 1, 5, "§f>> §a§lГотово §f<<");


	private LinkedList<Integer> checked = new LinkedList<>();
	private BiConsumer<Player, LinkedList<Integer>> onDone;

	public CheckBoxGUI(int boxes, String title, BiConsumer<Player, LinkedList<Integer>> onDone, boolean temporary) {
		super(create(18, title), temporary);
		inv().setItem(17, DONE);
		this.onDone = onDone;
	}

	public int addItem(ItemStack i) {
		int slot = inv().firstEmpty();
		inv().setItem(slot, i);
		return slot;
	}

	public void shiftClick(Player p, int slot) {
		if (slot == inv().getSize() - 1) {
			onDone.accept(p, checked);
			return;
		}
		ItemStack item = inv().getItem(slot);
		if (item == null || item.getType() == Material.AIR) return;
		if (slot % 18 > 8) {
			if (item.getType() != Material.BARRIER) return;
			inv().setItem(slot, new ItemStack(Material.AIR));
			checked.remove(new Integer(slot));
			p.updateInventory();
		} else {
			checked.add(slot);
			inv().setItem(slot + 9, UNCHECK);
			p.updateInventory();
		}
	}

	@Override
	public void click(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		int slot = e.getRawSlot();
		if (checked.isEmpty()) {
			checked.add(slot);
			onDone.accept(p, checked);
			checked = new LinkedList<>();
		} else shiftClick(p, slot);
	}
}
