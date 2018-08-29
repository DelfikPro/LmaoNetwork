package pro.delfik.lmao.outward.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemBuilder {
	
	private final Material material;
	private short damage = 0;
	private int amount = 1;
	private String displayName = null;
	private List<String> lore = null;
	private boolean unbreakable = false;
	private Set<Ench> enchantments = new HashSet<>();
	
	public ItemBuilder(Material m) {
		this.material = m;
	}

	public static ItemStack create(Material m, String name) {
		return new ItemBuilder(m).withDisplayName(name).build();
	}
	public static ItemStack create(Material m, String name, String... lore) {
		return new ItemBuilder(m).withDisplayName(name).withLore(lore).build();
	}

	public static ItemStack applyMeta(ItemStack itemStack, String display, String... lore) {
		ItemMeta meta = itemStack.getItemMeta();
		if (display != null) meta.setDisplayName(display);
		if (lore.length != 0) meta.setLore(Arrays.asList(lore));
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	public ItemStack build() {
		ItemStack itemStack = new ItemStack(material, amount, damage);
		ItemMeta meta = itemStack.getItemMeta();
		if (displayName != null) meta.setDisplayName(displayName);
		if (lore != null && !lore.isEmpty()) meta.setLore(lore);
		if (unbreakable) meta.spigot().setUnbreakable(true);
		itemStack.setItemMeta(meta);
		enchantments.forEach(e -> e.applyTo(itemStack));
		return itemStack;
	}
	
	public ItemBuilder withData(int data) {
		this.damage = (short) data;
		return this;
	}
	
	public ItemBuilder withAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public ItemBuilder withDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}
	
	public ItemBuilder withLore(String... lore) {
		this.lore = Arrays.asList(lore);
		return this;
	}
	
	public ItemBuilder enchant(Ench ench) {
		this.enchantments.add(ench);
		return this;
	}
	
	public ItemBuilder unbreakable() {
		this.unbreakable = true;
		return this;
	}
	
}