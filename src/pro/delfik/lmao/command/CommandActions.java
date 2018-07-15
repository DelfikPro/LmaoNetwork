package pro.delfik.lmao.command;

import lib.Converter;
import lib.Generate;
import lib.gui.CheckBoxGUI;
import lib.gui.GUI;
import lib.gui.GUILoading;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.core.connection.database.Database;
import pro.delfik.lmao.core.connection.database.ServerIO;
import pro.delfik.util.Rank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

public class CommandActions extends LmaoCommand {
	
	private final static ItemStack MUTE = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 4, "§f>> §e§lЗамутить §f<<", "§e§oАналог команды §7/mute");
	private final static ItemStack KICK = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 1, "§f>> §e§lКикнуть §f<<",
			"§e§oАналог команды §7/kick [Ник] Помеха игровому процессу");
	private final static ItemStack BAN = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 14, "§f>> §e§lЗабанить §f<<", "§e§oАналог команды §7/ban");
	
	public CommandActions() {
		super("actions", Rank.PLAYER, "Действия с игроком");
	}
	
	
	@Override
	public void run(CommandSender sender, String command, String[] args) {
		Person s = Person.get(sender);
		s.getHandle().openInventory(GUILoading.i());
		GUI gui = new GUI(Bukkit.createInventory(null, 9, "§0§lДействия с " + args[0]));
		boolean online = "true".equals(ServerIO.connect("online " + args[0]));
		
		ProhibitionInfo mute = ProhibitionInfo.load(args[0], false);
		ProhibitionInfo ban = ProhibitionInfo.load(args[0], true);
		
		ItemStack muteItem = mute == null ? MUTE : Generate.itemstack(Material.BARRIER,1, 0, "§f>> §a§lРазмутить §f<<",
				"§6Причина мута: §e" + mute.reason, "§6Модератор: §e" + mute.moder, "§6Мут до: §e" + mute.until);
		ItemStack banItem = ban == null ? BAN : Generate.itemstack(Material.BARRIER,1, 0, "§f>> §a§lРазбанить §f<<",
				"§cПричина бана: §e" + ban.reason, "§cМодератор: §e" + ban.moder, "§cБан до: §e" + ban.until);
		
		if (s.hasRank(Rank.RECRUIT)) {
			gui.put(3, muteItem, pl -> {
				if (mute != null) {
					ServerIO.connect("unmute " + args[0] + " " + sender.getName());
					pl.closeInventory();
					return;
				}
				pl.openInventory(GUILoading.i());
				pl.openInventory(muteGUI(args[0]));
			});
			if (online) gui.put(4, KICK, pl -> {
				ServerIO.connect("kick " + args[0] + " " + sender.getName() + " Помеха игровому процессу");
				pl.closeInventory();
				pl.sendMessage("§aИгрок §e" + args[0] + "§a кикнут с сервера.");
			});
			gui.put(5, banItem, pl -> {
				if (ban != null) {
					ServerIO.connect("unban " + args[0] + " " + sender.getName());
					pl.closeInventory();
					return;
				}
				pl.openInventory(GUILoading.i());
				pl.openInventory(banGUI(args[0]));
			});
		} else gui.put(4, new ItemStack(Material.COOKIE), Player::closeInventory);
		((Player) sender).openInventory(gui.getInventory());
	}
	
	
	private static class ProhibitionInfo {
		private String moder;
		private String until;
		private String reason;
		private boolean ban;
		
		ProhibitionInfo(String moder, String until, String reason, boolean ban) {
			this.moder = moder;
			this.until = until;
			this.reason = reason;
			this.ban = ban;
		}
		
		static ProhibitionInfo load(String player, boolean ban) {
			String prepareddQuery = "SELECT * FROM " + (ban ? "Bans" : "Mutes") + " WHERE " + (ban ? "name" : "player") + " = '" + player + "'";
			//Bukkit.broadcastMessage("§dPreparedQuery: §e" + prepareddQuery);
			Database.Result result = Database.sendQuery(prepareddQuery);
			if (result == null) return null;
			ResultSet set = result.set;
			try {
				if (!set.next()) return null;
				else return new ProhibitionInfo(set.getString("moderator"), set.getString("until"), set.getString("reason"), ban);
			} catch (SQLException e) {
				return null;
			}
		}
	}
	
	private enum MuteRule {
		CAPS(10, 10, "Капс", false),
		FLOOD(30, 30, "Флуд", false),
		CHAR_FLOOD(30, 30, "Флуд символами", false),
		SWEAR(60, 60, "Мат", false),
		AFFRONT(120, 2, "Оскорбление", true),
		AMORAL(240, 4, "Аморальное поведение", true);
		
		private final int time;
		private final int itemAmount;
		private final String rule;
		private final boolean changeIcon;
		private ItemStack item = null;
		
		MuteRule(int time, int itemAmount, String rule, boolean changeIcon) {
			this.time = time;
			this.itemAmount = itemAmount;
			this.rule = rule;
			this.changeIcon = changeIcon;
		}
		
		public ItemStack toItem() {
			return item != null ? item : (item = Generate.itemstack(changeIcon ? Material.REDSTONE_BLOCK : Material.GOLD_BLOCK, itemAmount, 0,
					"§6Мут за §e" + rule, "§6Длительность: §e" + (changeIcon ? time / 60 : time) + (changeIcon ?
																											Converter.plural(time / 60, " час", " часа", " часов") : Converter.plural(time, " минута", " минуты", " минут"))));
		}
	}
	
	private static Inventory muteGUI(String player) {
		MuteRule[] values = MuteRule.values();
		CheckBoxGUI gui = new CheckBoxGUI(9, "§0§lШаблоны мутов", (p, c) -> {
			EnumSet<MuteRule> result = EnumSet.noneOf(MuteRule.class);
			int time = 0;
			for (Integer i : c) {
				MuteRule r = values[i];
				time += r.time;
				result.add(r);
			}
			String reason = Converter.merge(result, r -> r.rule, ", ");
			ServerIO.connect("mute " + player + " " + p.getName() + " " + time + " " + reason);
			p.closeInventory();
		});
		for (MuteRule muteRule : values) gui.addItem(muteRule.toItem());
		return gui.getInventory();
	}
	
	private enum BanRule {
		USE_BUGS(10080, "7 дней", "Багоюз", Material.CARROT_STICK),
		CHEATS(0, "Навсегда", "Читы", Material.COMMAND),
		INCORRECT_NAME(0, "Навсегда", "Недопустимый никнейм", Material.NAME_TAG),
		MULTIACCOUNT(0, "Навсегда", "Мультиаккаунт", Material.SKULL_ITEM);
		
		private final int time;
		private final String textTime;
		private final String rule;
		private final Material icon;
		private ItemStack item = null;
		
		BanRule(int time, String textTime, String rule, Material icon) {
			this.time = time;
			this.textTime = textTime;
			this.rule = rule;
			this.icon = icon;
		}
		
		public ItemStack toItem() {
			return item != null ? item : (item = Generate.itemstack(icon, 1, 0,
					"§cБан за §e" + rule, "§cДлительность: §e" + textTime));
		}
	}
	
	private static Inventory banGUI(String player) {
		BanRule[] values = BanRule.values();
		GUI gui = new GUI(Bukkit.createInventory(null, 9, "§0§lШаблоны банов"));
		for (int i = 0; i < values.length; i++) {
			BanRule rule = values[i];
			gui.put(i, rule.toItem(), p -> {
				ServerIO.connect("ban " + player + " " + p.getName() + " " + rule.time + " " + rule.rule);
				p.closeInventory();
			});
		}
		return gui.getInventory();
	}
}
