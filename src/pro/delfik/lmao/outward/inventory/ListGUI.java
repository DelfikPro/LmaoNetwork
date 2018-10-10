package pro.delfik.lmao.outward.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ListGUI extends GUI {

	protected ListGUI(String title, int rows) {
		super(create(rows * 9, title), false);

	}

	@Override
	public void click(InventoryClickEvent event) {

	}

	public static class Entry {
		private final Consumer<Player> action;
		private final ItemStack item;

		public Entry(Consumer<Player> action, ItemStack item) {
			this.action = action;
			this.item = item;
		}

		public Consumer<Player> getAction() {
			return action;
		}

		public ItemStack getItem() {
			return item;
		}
	}
}
