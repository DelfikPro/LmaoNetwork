package pro.delfik.lmao.outward.item;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.outward.Generate;

import java.util.Arrays;

public final class I {
	private I() {}

	public static boolean compare(ItemStack a, ItemStack b, boolean dataMatter) {
		if (a.getType() != b.getType()) return false;
		if (dataMatter && a.getDurability() != b.getDurability()) return false;
		if (!(a.hasItemMeta() || b.hasItemMeta())) return true;
		if (a.hasItemMeta() && b.hasItemMeta()) {
			ItemMeta A = a.getItemMeta(), B = b.getItemMeta();
			if (A.hasDisplayName() && B.hasDisplayName())
				if (!A.getDisplayName().equals(B.getDisplayName())) return false;
			if (A.hasLore() && B.hasLore()) if (!A.getLore().equals(B.getLore())) return false;
			return true;
		} else return false;
	}
	
	public static BukkitScheduler s() {
		return Bukkit.getScheduler();
	}
	
	public static BukkitTask delay(Runnable r, int ticks) {
		return s().runTaskLater(Lmao.plugin, r, ticks);
	}
	public static BukkitTask timer(Runnable r, int ticks) {
		return s().runTaskTimer(Lmao.plugin, r, ticks, ticks);
	}
	
	public static ItemStack apply(ItemStack i, String name, String... lore) {
		ItemMeta m = i.getItemMeta();
		if (name != null) m.setDisplayName(name);
		if (lore != null) m.setLore(Arrays.asList(lore));
		m.addItemFlags(ItemFlag.values());
		i.setItemMeta(m);
		return i;
	}
	
	
	public static ItemStack create(Material type, int amount, short data, String name, String... lore) {
		return apply(new ItemStack(type, amount, data), name, lore);
	}
	public static ItemStack create(Material m, String name, String... lore) {
		return apply(new ItemStack(m), name, lore);
	}
	public static ItemStack create(Material m, short data, String name, String... lore) {
		return apply(new ItemStack(m, data), name, lore);
	}
	public static ItemStack star(Color color, String name, String... lore) {
		ItemStack i = Generate.itemstack(Material.FIREWORK_CHARGE, 1, 0, name, lore);
		ItemMeta m = i.getItemMeta();
		FireworkEffectMeta metaFw = (FireworkEffectMeta) m;
		FireworkEffect aa = FireworkEffect.builder().withColor(color).build();
		metaFw.setEffect(aa);
		m.addItemFlags(ItemFlag.values());
		i.setItemMeta(m);
		return i;
	}
	
	public static ItemStack unbreak(ItemStack i) {
		ItemMeta m = i.getItemMeta();
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}
	
	public static ItemStack enchant(ItemStack i, Enchantment e, int level) {
		i.addUnsafeEnchantment(e, level);
		return i;
	}
	
	public static ItemStack enchant(ItemStack i, String... s) {try {
		for (String a : s) {
			if (!a.replaceAll("\\d{1,}:\\d{1,}", "").equals("")) {
				throw new IllegalArgumentException();
			}
			String m[] = a.split(":");
			i.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(m[0])), Integer.parseInt(m[1]));
		}
		return i;
	} catch (NumberFormatException e) {throw new IllegalArgumentException();}}
}