package pro.delfik.lmao.outward.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Меню в инвентаре.
 */
public abstract class GUI {

	private static final Map<Inventory, GUI> ALL = new HashMap<>();
	private final Inventory inv;
	private final boolean temporary;

	protected GUI(Inventory inv, boolean temporary) {
		this.inv = inv;
		this.temporary = temporary;
		ALL.put(inv, this);
	}

	/**
	 * Обработать клик по инвентарю.
	 * @param event Событие клика по инвентарю для обработки.
	 */
	public abstract void click(InventoryClickEvent event);

	/**
	 * Получение инвентаря.
	 * @return Инвентарь, в котором находится GUI.
	 */
	public Inventory inv() {
		return inv;
	}

	/**
	 * Является ли этот GUI временным:
	 * Временный GUI удаляется, как только игрок закрывает его.
	 * Постоянный GUI удаляется только после вызова метода {@code remove()}.
	 * @return true или false
	 */
	public boolean isTemporary() {
		return temporary;
	}

	/**
	 * Удалить GUI из памяти.
	 */
	public void remove() {
		ALL.remove(inv);
		for (HumanEntity player : inv.getViewers()) {
			player.closeInventory();
			player.sendMessage("§cGUI удалён.");
		}
	}

	/**
	 * Создание ивентаря.
	 * @param slots Количество слотов в инвентаре (должно быть кратно 9)
	 * @param title Название инвентаря
	 * @return Инвентарь
	 */
	public static Inventory create(int slots, String title) {
		return Bukkit.createInventory(null, slots, title);
	}

	/**
	 * Получение GUI по инвентарю.
	 * @param inv Инвентарь, в котором находится GUI.
	 * @return null, если GUI не найден, иначе GUI.
	 */
	public static GUI get(Inventory inv) {
		return ALL.get(inv);
	}

	/**
	 * @return Все зарегистрированные GUI.
	 */
	public static Collection<GUI> getAll() {
		return ALL.values();
	}

}
