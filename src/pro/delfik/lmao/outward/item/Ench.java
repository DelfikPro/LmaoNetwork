package pro.delfik.lmao.outward.item;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

import static org.bukkit.enchantments.Enchantment.ARROW_INFINITE;
import static org.bukkit.enchantments.Enchantment.DAMAGE_ALL;
import static org.bukkit.enchantments.Enchantment.DIG_SPEED;
import static org.bukkit.enchantments.Enchantment.KNOCKBACK;
import static org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL;

public class Ench {

	public static final Ench

		PROTECTION_1 = new Ench(PROTECTION_ENVIRONMENTAL, 1),
		PROTECTION_2 = new Ench(PROTECTION_ENVIRONMENTAL, 2),
		PROTECTION_3 = new Ench(PROTECTION_ENVIRONMENTAL, 3),
		PROTECTION_4 = new Ench(PROTECTION_ENVIRONMENTAL, 4),

		SHARPNESS_1 = new Ench(DAMAGE_ALL, 1),
		SHARPNESS_2 = new Ench(DAMAGE_ALL, 2),
		SHARPNESS_3 = new Ench(DAMAGE_ALL, 3),
		SHARPNESS_4 = new Ench(DAMAGE_ALL, 4),
		SHARPNESS_5 = new Ench(DAMAGE_ALL, 5),

		FIRE_ASPECT = new Ench(Enchantment.FIRE_ASPECT, 1),

		KNOCKBACK_1 = new Ench(KNOCKBACK, 1),
		KNOCKBACK_2 = new Ench(KNOCKBACK, 2),

		EFFIECENCY_1 = new Ench(DIG_SPEED, 1),
		EFFIECENCY_2 = new Ench(DIG_SPEED, 2),
		EFFIECENCY_3 = new Ench(DIG_SPEED, 3),
		EFFIECENCY_4 = new Ench(DIG_SPEED, 4),
		EFFIECENCY_5 = new Ench(DIG_SPEED, 5),

		SILK_TOUCH = new Ench(Enchantment.SILK_TOUCH, 1),

		POWER_1 = new Ench(Enchantment.ARROW_DAMAGE, 1),
		POWER_2 = new Ench(Enchantment.ARROW_DAMAGE, 2),
		POWER_3 = new Ench(Enchantment.ARROW_DAMAGE, 3),
		POWER_4 = new Ench(Enchantment.ARROW_DAMAGE, 4),
		POWER_5 = new Ench(Enchantment.ARROW_DAMAGE, 5),

		INFINITY = new Ench(ARROW_INFINITE, 1),
		ARROW_FIRE = new Ench(Enchantment.ARROW_FIRE, 1),
		ARROW_KNOCKBACK = new Ench(Enchantment.ARROW_KNOCKBACK, 1);


	private final Enchantment enchantment;
	private final int level;
	
	public Ench(Enchantment enchantment, int level) {
		this.enchantment = enchantment;
		this.level = level;
	}
	public Ench(Map.Entry<Enchantment, Integer> enchLevel) {
		this.enchantment = enchLevel.getKey();
		this.level = enchLevel.getValue();
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
		return o instanceof Ench && enchantment.equals(((Ench) o).getEnchantment());
	}
}
