package pro.delfik.lmao.util;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pro.delfik.lmao.ev.EvChat;
import pro.delfik.lmao.Lmao;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.Connect;
import implario.net.packet.PacketSummon;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class U {

	/*
	* Селекторы
	 */
	
	public static Collection<Person> selector(CommandSender sender, String selector) {
		if (selector.equals("@a")) return Person.online();
		Person person = unary(sender, selector);
		return person == null ? Collections.emptyList() : Collections.singletonList(person);
	}
	public static void selector(CommandSender sender, String selector, Consumer<Person> ps) {
		Collection<Person> list = selector(sender, selector);
		if (list.isEmpty()) {sender.sendMessage(Lmao.p() + "Игрок §e" + selector + "§c не найден на вашем " +
														"сервере."); return;}
		list.forEach(ps);
	}
	
	public static Person unary(CommandSender sender, String selector) {
		Person p = Person.get(selector);
		if (p == null) return null;
		if (sender instanceof Player) {
			if (((Player) sender).getWorld().equals(p.getHandle().getWorld()) && !p.isVanish()) return p;
			else return null;
		} else return p;
	}


	public static GameMode getGamemode(String string) {
		switch (string.toLowerCase()) {
			case "c": case "creative": case "1": return GameMode.CREATIVE;
			case "survival": case "s": case "0": return GameMode.SURVIVAL;
			case "adventure": case "a": case "2": return GameMode.ADVENTURE;
			case "3": case "spectator": case "watcher": case "w": return GameMode.SPECTATOR;
			default: return null;}
	}
	public static String getGamemodeRepresentation(GameMode gm) {
		switch (gm) {
			case ADVENTURE: return "Приключение";
			case CREATIVE: return "Творческий";
			case SPECTATOR: return "Наблюдатель";
			case SURVIVAL: return "Выживание";
			default: return "Неизвестный режим";
		}
	}


	// -------------------------------------------- Работа с чатом
	
	public static final char[] COLOR_CHARS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
			'b', 'c', 'd', 'e', 'f', 'k', 'l', 'm', 'n', 'o', 'r'};
	public static String color(String m) {
		m = m.replace("&&", "§§");
		for (char c : COLOR_CHARS) m = m.replace("&" + c, "§" + c);
		m = m.replace("<3", "\u2764");
		m = m.replace(":star:", "\u2605");
		m = m.replace(":sword:", "§6§l§m--[§b§l§m---§f");
		m = m.replace("Жс", ":c");
		m = m.replace("сЖ", "c:");
		return m.replace("§§", "&");
	}
	
	public static void msg(CommandSender s, Object... o) {
		if (s instanceof Player) ((Player) s).spigot().sendMessage(constructComponent(o));
		else s.sendMessage(constructComponent(o).toPlainText());
	}
	public static void send(TextComponent text, Collection<? extends Player> players) {
		if (players == null) players = Bukkit.getOnlinePlayers();
		for (Player player : players) player.spigot().sendMessage(text);
	}


	public static void bclist(List<Object> o) {
		TextComponent c = constructFromList(o);
		for (Player p : Bukkit.getOnlinePlayers()) p.spigot().sendMessage(c);
		Bukkit.getLogger().info(c.toPlainText());
	}

	public static void bc(Object... o) {
		TextComponent c = constructComponent(o);
		for (Player p : Bukkit.getOnlinePlayers()) p.spigot().sendMessage(c);
		Bukkit.getLogger().info(c.toPlainText());
	}
	
	public static TextComponent hover(String text, String hover) {
		TextComponent c = new TextComponent(text);
		c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											  new TextComponent[] {new TextComponent(hover)}));
		return c;
	}
	
	public static TextComponent simple(String text, String hover, String suggest) {
		TextComponent c = new TextComponent(text);
		c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {new TextComponent(hover)}));
		c.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest));
		return c;
	}

	public static TextComponent run(String text, String hover, String suggest) {
		TextComponent c = new TextComponent(text);
		c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											  new TextComponent[] {new TextComponent(hover)}));
		c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, suggest));
		return c;
	}
	
	public static TextComponent constructFromList(List<Object> o) {
		TextComponent c = new TextComponent("§7");
		for (Object ob : o) c.addExtra(constructComponent0(ob));
		return c;
	}

	public static TextComponent constructComponent(Object... o) {
		TextComponent c = new TextComponent("§7");
		for (Object ob : o) c.addExtra(constructComponent0(ob));
		return c;
	}
	private static TextComponent constructComponent0(Object o) {
		TextComponent comp = new TextComponent();
		if (o instanceof Player) {
			comp.setText("§7" + ((Player) o).getDisplayName());
			comp.setHoverEvent(getHover((Player) o));
			comp.setClickEvent(getClick((Player) o));
		} else if (o instanceof TextComponent) {
			comp = (TextComponent) o;
		} else if (o instanceof Person) {
			comp.setText(((Person) o).getDisplayName());
			comp.setHoverEvent(getHover(((Person) o).getHandle()));
			comp.setClickEvent(getClick(((Person) o).getHandle()));
		} else if (o instanceof EvChat.Link) {
			comp.setText(EvChat.decorateUrl(((EvChat.Link) o).getDest()));
			comp.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ((EvChat.Link) o).getDest()));
			comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
													 new TextComponent[] {new TextComponent("§eСсылка:\n§a§n" + ((EvChat.Link) o).getDest() + "\n§e >> Нажмите для перехода <<")}
			));
		}
		else comp.setText(o.toString());
		return comp;
	}
	
	private static ClickEvent getClick(Player p) {
		return new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/player " + p.getName());
	}
	
	private static HoverEvent getHover(Player p) {
		return new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {
				new TextComponent("§7" + p.getDisplayName() + "\n§e> Нажмите для списка действий §e<")
		});
	}
	
	public static void send(String player, String server) {
		Connect.send(new PacketSummon(player, server));
	}

	public static Location toLocation(String location, World world) {
		String[] args = location.split(", ");
		Location loc = new Location(world, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		if (args.length == 5) {
			loc.setYaw(Float.parseFloat(args[3]));
			loc.setPitch(Float.parseFloat(args[4]));
		}
		return loc;
	}

	public static World world(){
		return Bukkit.getWorlds().get(0);
	}
}
