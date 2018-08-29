package pro.delfik.lmao.outward.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class PotionBuilder extends ItemBuilder {
	private final PotionType type;

	public PotionBuilder(PotionType type) {
		super(Material.POTION);
		this.type = type;
	}

	@Override
	public ItemStack build() {
		return super.build();
	}
}
