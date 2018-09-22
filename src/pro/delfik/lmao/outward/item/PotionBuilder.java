package pro.delfik.lmao.outward.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

public class PotionBuilder extends ItemBuilder {
	private final PotionType type;
	private int amplifier = 0;
	private int duration = 1;

	public PotionBuilder(PotionType type) {
		super(Material.POTION);
		this.type = type;
	}

	public PotionBuilder withAmplifier(int amplifier) {
		this.amplifier = amplifier;
		return this;
	}

	public PotionBuilder withDuration(int duration) {
		this.duration = duration;
		return this;
	}

	@Override
	public ItemStack build() {
		ItemStack basic = super.build();
		Potion potion = new Potion(type, amplifier + 1);
		potion.apply(basic);
		PotionMeta meta = (PotionMeta) basic.getItemMeta();
		meta.addCustomEffect(new PotionEffect(type.getEffectType(), duration, amplifier, true), true);
		meta.setMainEffect(type.getEffectType());
		basic.setItemMeta(meta);
		return basic;
	}
}
