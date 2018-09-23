package pro.delfik.lmao.outward.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.Lmao;
import pro.delfik.lmao.outward.Generate;

public class GUILoading extends GUI {

	public static final Inventory loading = create(9, "§lЗагрузка...");
	public static volatile int load = 0;
	private static final ItemStack green = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 5, "§a§lЗагрузка...", "§e§oВсё заработает совсем скоро ^_^");
	private static final ItemStack yellow = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 4, "§a§lЗагрузка...", "§e§oВсё заработает совсем скоро ^_^");

	public static void startLoop() {
		new pro.delfik.lmao.outward.gui.GUI(loading);
		Bukkit.getScheduler().runTaskTimer(Lmao.plugin, () -> {
			loading.setItem(load % 9, load > 8 ? yellow : green);
			if (++load > 17) load = 0;
		}, 2L, 2L);
	}


	protected GUILoading(Inventory inv, boolean temporary) {
		super(inv, temporary);
	}

	@Override
	public void click(InventoryClickEvent event) {}
}
