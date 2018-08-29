package pro.delfik.lmao.outward.item;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Ench {
	private final Enchantment enchantment;
	private final int level;
	
	public Ench(Enchantment enchantment, int level) {
		this.enchantment = enchantment;
		this.level = level;
	}
	
	public Enchantment getEnchantment() {
		return enchantment;
	}
	public int getLevel() {
		return level;
	}
	public ItemStack applyTo(ItemStack item) {
		assert item != null;
		item.addUnsafeEnchantment(enchantment, level);
		return item;
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Ench && enchantment.equals(((Ench) o).getEnchantment());
	}
}
