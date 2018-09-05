package pro.delfik.lmao.outward.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SlotGUI extends GUI {

	private final Action click, shift;

	/**
	 * GUI, который выполняет один и тот же заданный код для кликов по всем слотам.
	 * @param title Название инвентаря (Строчка сверху)
	 * @param rows Количество строк в инвентаре
	 * @param action (player, slot, itemstack) -> {...} - код, который выполняется при нажатии
	 */
	public SlotGUI(String title, int rows, Action action) {
		this(title, rows, action, null);
	}

	/**
	 * GUI, который выполняет один и тот же заданный код для кликов по всем слотам.
	 * @param title Название инвентаря (Строчка сверху)
	 * @param rows Количество строк в инвентаре
	 * @param action (player, slot, itemstack) -> {...} - код, который выполняется при нажатии
	 * @param shiftAction Код, который выполняется при нажатии с зажатым Shift (можно оставить null)
	 */
	public SlotGUI(String title, int rows, Action action, Action shiftAction) {
		super(create(rows * 9, title), false);
		click = action;
		shift = shiftAction;
	}

	@Override
	public void click(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player p = (Player) event.getWhoClicked();
		int slot = event.getRawSlot();
		if (slot >= inv().getSize()) return;
		if (item == null) return;
		if (shift != null && event.isShiftClick()) shift.apply(p, slot, item);
		else click.apply(p, slot, item);
	}

	@FunctionalInterface
	public interface Action {
		void apply(Player p, int slot, ItemStack item);
	}
}
