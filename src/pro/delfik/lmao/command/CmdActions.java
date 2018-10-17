package pro.delfik.lmao.command;

import implario.net.packet.PacketPunishment;
import implario.util.Converter;
import implario.util.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.Connect;
import pro.delfik.lmao.outward.Generate;
import pro.delfik.lmao.outward.gui.CheckBoxGUI;
import pro.delfik.lmao.outward.gui.GUI;
import pro.delfik.lmao.outward.gui.GUILoading;

import java.util.EnumSet;

@Cmd(name = "actions", description = "Действия с игроком", args = 1, help = "[Игрок]")
public class CmdActions extends LmaoCommand {
	private final static ItemStack MUTE = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 4, "§f>> §e§lЗамутить §f<<", "§e§oАналог команды §7/mute");
	private final static ItemStack KICK = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 1, "§f>> §e§lКикнуть §f<<",
			"§e§oАналог команды §7/kick [Ник] Помеха игровому процессу");
	private final static ItemStack BAN = Generate.itemstack(Material.STAINED_GLASS_PANE, 1, 14, "§f>> §e§lЗабанить §f<<", "§e§oАналог команды §7/ban");

	@Override
	public void run(Person person, String args[]) {
		String player = args[0];
		
		person.getHandle().openInventory(GUILoading.i());
		GUI gui = new GUI(Bukkit.createInventory(null, 9, "§0§lДействия с " + args[0]));
		boolean online = Person.get(player) != null;
		
		if (person.hasRank(Rank.RECRUIT)) {
			gui.put(3, MUTE, pl -> {
				pl.openInventory(GUILoading.i());
				pl.openInventory(muteGUI(args[0]));
			});
			if (online) gui.put(4, KICK,
					pl -> Connect.send(new PacketPunishment(player, PacketPunishment.Punishment.KICK, person.getName(), 0, "Помеха игровому процессу")));
			gui.put(5, BAN, pl -> {
				pl.openInventory(GUILoading.i());
				pl.openInventory(banGUI(args[0]));
			});
		} else gui.put(4, new ItemStack(Material.COOKIE), Player::closeInventory);
		person.getHandle().openInventory(gui.getInventory());
	}
	
	private enum MuteRule {
		CAPS(30, 30, "Капс", false),
		FLOOD(30, 30, "Флуд", false),
		CHAR_FLOOD(30, 30, "Флуд символами", false),
		SWEAR(60, 60, "Мат", false),
		AFFRONT(240, 4, "Оскорбление", true),
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
			Connect.send(new PacketPunishment(player, PacketPunishment.Punishment.MUTE, p.getName(), time, reason));
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
				Connect.send(new PacketPunishment(player, PacketPunishment.Punishment.BAN, p.getName(), rule.time, rule.rule));
				p.closeInventory();
			});
		}
		return gui.getInventory();
	}
}
