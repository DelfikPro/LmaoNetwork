package pro.delfik.lmao.outward.gui;

import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.outward.Generate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUILoading {
	public static final Inventory loading = Bukkit.createInventory(null, 9, "§lЗагрузка...");
	public static volatile int load = 0;
	private static final ItemStack green = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 5, "§a§lЗагрузка...", "§e§oВсё заработает совсем скоро ^_^");
	private static final ItemStack yellow = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 4, "§a§lЗагрузка...", "§e§oВсё заработает совсем скоро ^_^");
	
	public static void startLoop() {
		new GUI(loading);
		Bukkit.getScheduler().runTaskTimer(Lmao.plugin, () -> {
			loading.setItem(load % 9, load > 8 ? yellow : green);
			if (++load > 17) load = 0;
		}, 2L, 2L);
	}
	
	public static Inventory i() {
		return loading;
	}
	
}
