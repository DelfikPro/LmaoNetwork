package pro.delfik.lmao.outward;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.outward.item.I;

import static implario.util.Exceptions.runThrowsEx;

public final class Generate {
	private Generate() {}

	public static Inventory inventory(String title, ItemStack... is) {return inventory(title, 0, is);}
	public static Inventory inventory(String title, int size, ItemStack... is) {
		if (size == 0) size = (is.length / 9 + 1) * 9;
		Inventory inv = Bukkit.createInventory(null, size, title);
		inv.setContents(is);
		return inv;
	}
	
	public static ItemStack itemstack(Material type, int amount, int data, String name, String... lore) {
		return I.create(type, amount, (short) data, name, lore);
	}
	
	public static ItemStack charge(Color color, String name, String... lore) {
		return I.star(color, name, lore);
	}
	
	public static ItemStack unbreak(ItemStack i) {
		return I.unbreak(i);
	}
	
	public static ItemStack enchant(ItemStack i, Enchantment e, int level) {
		i.addUnsafeEnchantment(e, level);
		return i;
	}

	public static void firework(Location location, boolean withoutparticles) {
		Firework f = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
		FireworkMeta fm = f.getFireworkMeta(); Type t = Type.BALL_LARGE; Color c = Color.WHITE;
		FireworkEffect ef = FireworkEffect.builder().flicker(false).withColor(c).with(t).build(); fm.addEffect(ef); fm.setPower(0); f.setFireworkMeta(fm);
		if (withoutparticles) {f.detonate(); return;} 
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lmao.plugin, () -> runThrowsEx(f::detonate), (2));
	}
	public static void firework(Player p, boolean withoutparticles) {
		Firework f = (Firework) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
		FireworkMeta fm = f.getFireworkMeta(); Type t = Type.BALL_LARGE; Color c = Color.WHITE;
		FireworkEffect ef = FireworkEffect.builder().flicker(false).withColor(c).with(t).build(); fm.addEffect(ef); fm.setPower(0); f.setFireworkMeta(fm);
		if (withoutparticles) {f.detonate(); return;} 
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lmao.plugin, () -> {try {f.teleport(p); f.detonate();} catch (Exception ignored) {}}, (2));
	}
	
	public static ItemStack potion(ItemStack i, PotionEffect... e) {
		PotionMeta pm;
		try {pm = (PotionMeta) i.getItemMeta();} catch (ClassCastException ex) {return i;}
		for (PotionEffect ef : e) {
			pm.addCustomEffect(ef, true);
		}
		i.setItemMeta(pm);
		return i;
	}
	
	
	public static boolean equalsItem(ItemStack i1, ItemStack i2) {
		return I.compare(i1, i2, true);
	}
}
