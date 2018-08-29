package pro.delfik.lmao.outward.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;


public class ListGUI<T> extends GeneralizedGUI {

	private final List<Entry<T>> list;

	public ListGUI(int size, String title, BiConsumer<Player, Integer> action) {
		super(Bukkit.createInventory(null, (size + 9) / 9, title), action, null);
		list = new FixedArrayList<>((size + 9) / 9);
	}

	public void add(Entry<T> entry) {
		int pos = list.size();
		list.add(entry);
		put(pos, entry.item, null);
	}

	public void update(int slot) {
		Entry<T> entry = list.get(slot);
		inv.setItem(slot, entry == null ? new ItemStack(Material.AIR) : entry.item);
	}

	public void remove(Entry<T> entry) {
	}

	public static class Entry<T> {
		public final ItemStack item;
		public final T key;
		public Entry(ItemStack item, T key) {
			this.item = item;
			this.key = key;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Entry)) return false;
			return key.equals(((Entry<T>) o).key);
		}
	}

}
