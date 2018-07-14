package pro.delfik.lmao.permissions;

import lib.Generate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.util.U;

public class PermsManager implements Listener {
	
	public static Inventory generateInv(String name) {
		return Bukkit.createInventory(null, 9, "Скоро!");
	}
	
	private static ItemStack rankToItem(String player, Rank rank) {
		return Generate.itemstack(Material.WOOL, 1, U.woolFromColor(rank.getNameColor()),
				"§eНазначить статус" + " " + rank.getNameColor() + rank.getName(), "setrank." + rank.name());
	}
	
//	@EventHandler
//	public void onInventoryClick(InventoryClickEvent e) {
//		try {
//			if (!e.getInventory().getTitle().startsWith("Управление игроком ")) return;
//			e.setCancelled(true);
//			String playername = e.getInventory().getTitle().replace("Управление игроком ", "");
//			__google_.util.Rank rank = __google_.util.Rank.valueOf(e.getCurrentItem().getItemMeta().getLore().get(0).replace("setrank.", ""));
//			if (!Core.check(e.getWhoClicked(), rank)) {
//				e.getWhoClicked().closeInventory();
//				e.getWhoClicked().sendMessage(Lmao.p() + "Вы не можете выдавать ранги выше вашего.");
//				return;
//			}
//			ServerIO.bungeeEvent("setrank", playername + "/" + rank.toString());
//		} catch (Exception ignored) {
//		}
//	}
}
