package pro.delfik.lmao.outward.item;

import implario.util.Converter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ItemBuilder {
	
	private final Material material;
	private short damage = 0;
	private int amount = 1;
	private String displayName = null;
	private List<String> lore = null;
	private boolean unbreakable = false;
	private Collection<Ench> enchantments;

	public ItemBuilder(ItemStack item) {
		material = item.getType();
		damage = item.getDurability();
		amount = item.getAmount();
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if (meta.hasDisplayName()) displayName = meta.getDisplayName();
			if (meta.hasLore()) lore = meta.getLore();
			if (meta.hasEnchants()) enchantments = Converter.transform(meta.getEnchants().entrySet(), Ench::new);
			unbreakable = meta.spigot().isUnbreakable();
		}
	}

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

	public static ItemStack setAmount(ItemStack item, int amount) {
		return new ItemBuilder(item).withAmount(amount).build();
	}

	public static ItemStack addLore(ItemStack item, String... lore) {
		item = item.clone();
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) meta.setLore(Converter.asList(lore));
		else {
			List<String> list = meta.getLore();
			Collections.addAll(list, lore);
			meta.setLore(list);
		}
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack build() {
		ItemStack itemStack = new ItemStack(material, amount, damage);
		ItemMeta meta = itemStack.getItemMeta();
		if (displayName != null) meta.setDisplayName(displayName);
		if (lore != null && !lore.isEmpty()) meta.setLore(lore);
		if (unbreakable) meta.spigot().setUnbreakable(true);
		itemStack.setItemMeta(meta);
		if (enchantments != null) enchantments.forEach(e -> e.applyTo(itemStack));
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

	public ItemBuilder addLore(String... lore) {
		if (this.lore == null) this.lore = Converter.asList(lore);
		else Collections.addAll(this.lore, lore);
		return this;
	}
	
	public ItemBuilder enchant(Ench... ench) {
		if (enchantments == null) enchantments = new HashSet<>();
		Collections.addAll(enchantments, ench);
		return this;
	}
	
	public ItemBuilder unbreakable() {
		this.unbreakable = true;
		return this;
	}
	
}