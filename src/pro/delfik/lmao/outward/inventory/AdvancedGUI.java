package pro.delfik.lmao.outward.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class AdvancedGUI extends GUI {

	private final Consumer<InventoryClickEvent> action;

	/**
	 * GUI, который обрабатывает InventoryClickEvent заданным образом.
	 * Полностью ручное управление.
	 * @param title Название инвентаря (Строчка сверху)
	 * @param rows Количество строк в инвентаре
	 * @param action event -> {...} - ручная обработка InventoryClickEvent.
	 */
	public AdvancedGUI(String title, int rows, Consumer<InventoryClickEvent> action) {
		super(create(rows * 9, title), false);
		this.action = action;
	}

	@Override
	public void click(InventoryClickEvent event) {
		action.accept(event);
	}
}
