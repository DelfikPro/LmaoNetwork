package pro.delfik.lmao.permissions;

import lib.Generate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.util.U;

//TODO
public class PermsManager implements Listener {
	
	public static Inventory generateInv(String name) {
		return Bukkit.createInventory(null, 9, "Скоро!");
	}
	
	private static ItemStack rankToItem(String player, Rank rank) {
		return Generate.itemstack(Material.WOOL, 1, U.woolFromColor(rank.getNameColor()),
				"§eНазначить статус" + " " + rank.getNameColor() + rank.getName(), "setrank." + rank.name());
	}
}
