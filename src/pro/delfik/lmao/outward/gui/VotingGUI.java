package pro.delfik.lmao.outward.gui;

import implario.util.Converter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.outward.item.ItemBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotingGUI extends GUI {

	private final Entry[] entries;
	private final Map<String, Integer> votes = new HashMap<>();

	public VotingGUI(Collection<Entry> entries) {
		super(Bukkit.createInventory(null, 54, "Голосование за карту"));
		if (entries.size() > 9) throw new IllegalArgumentException("Максимальное количество энтри - 9");
		this.entries = new Entry[entries.size()];
		entries.toArray(this.entries);
		for (int i = 0; i < this.entries.length; i++) setEntry(i, this.entries[i]);
	}

	public Entry[] getEntries() {
		return entries;
	}

	public int getVotes(int entry) {
		int result = 0;
		for (int i : votes.values()) if (i == entry) result++;
		return result;
	}

	public void setEntry(int slot, Entry entry) {
		ItemStack item = entry.item(getVotes(slot));
		put(slot, item, player -> vote(player, slot));
		update(slot);
	}

	public List<Entry> result() {
		int max = -1;
		List<Entry> winners = new ArrayList<>();
		for (int i = 0; i < entries.length; i++) {
			Entry entry = entries[i];
			int votes = getVotes(i);
			if (votes < max) continue;
			if (votes > max) {
				winners = new ArrayList<>();
				max = votes;
			}
			winners.add(entry);
		}
		return winners;
	}

	public void vote(Player player, int candidate) {
		int previous = -1;
		if (votes.containsKey(player.getName())) {
			previous = votes.get(player.getName());
			if (previous == candidate) {
				votes.remove(player.getName());
				update(candidate);
				player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.8f, 0.8f);
				return;
			}
		}
		votes.put(player.getName(), candidate);
		update(candidate);
		if (previous != -1) update(previous);
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.2f, 1.2f);
	}

	private static final ItemStack[] tiers = {
			new ItemBuilder(Material.STAINED_GLASS_PANE).withData(15).withDisplayName("§f").build(),
			new ItemBuilder(Material.STAINED_GLASS_PANE).withData(5 ).withDisplayName("§f").build(),
			new ItemBuilder(Material.STAINED_GLASS_PANE).withData(4 ).withDisplayName("§f").build(),
			new ItemBuilder(Material.STAINED_GLASS_PANE).withData(1 ).withDisplayName("§f").build(),
			new ItemBuilder(Material.STAINED_GLASS_PANE).withData(14).withDisplayName("§f").build()
	};

	public void update(int slot) {
		int votes = getVotes(slot);
		Entry e = entries[slot];
		getInventory().setItem(slot, e.item(votes));
		int[] colors = {0, 0, 0, 0, 0};
		for (int i = 0; i < votes; i++) {
			int pos = i % 5;
			colors[pos]++;
		}
		for (int i = 0; i < 5; i++) getInventory().setItem(slot + (i + 1) * 9, tiers[colors[i]]);
	}

	public static class Entry {
		public final Material material;
		public final String title;
		public final String key;

		public Entry(Material material, String title, String key) {
			this.material = material;
			this.title = title;
			this.key = key;
		}

		public ItemStack item(int votes) {
			return ItemBuilder.create(material, "§e§l" + title, "§f>> §a§lПроголосовать §f<<",
					"§a" + votesRepr(votes));
		}
		public String votesRepr(int votes) {
			return votes + " голос" + Converter.plural(votes, "", "а", "ов");
		}

		@Override
		public String toString() {
			return "'" + title + "'/" + key;
		}
	}
}
