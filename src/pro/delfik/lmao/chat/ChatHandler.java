package pro.delfik.lmao.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.permissions.Perms;
import pro.delfik.lmao.util.U;
import pro.delfik.util.Rank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatHandler implements Listener {
	private static boolean off = false;
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e) {
		if (off) return;
		String name = e.getPlayer().getName();
		String dname = e.getPlayer().getDisplayName();
		if (e.getMessage().contains("&")) if (!Perms.isEnough(e.getPlayer(), Rank.SPONSOR, true)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("LMAO §e> §cЦветной чат доступен со статуса §bСпонсор§c.");
			return;
		}
		
		Person u = Person.get(name);
		
		
		// Сообщение для цитирования
		String msg = U.color(e.getMessage());
		// Сообщение для отображения
		String toBc = "§7: §f" + msg.replace("[Q]", "§7§o\"§e§o").replace("[/Q]", "§7§o\" -§f");
		msg = msg.replaceAll("\\[Q\\].*\\[\\/Q\\]\\s", "");
		e.setCancelled(true); // Отменяем сообщение со скобками
		
		
		List<Object> msgblock = new ArrayList<>();
		msgblock.add(e.getPlayer());
		String hover = "§aСообщение от §e" + dname + "§a.\n§aОтправлено в §e" + time() + "\n§e> Нажмите, чтобы ответить <";
		String suggest = "[Q]" + msg.replaceAll("§.", "") + "[/Q] ";
		
		
		// Обработка ссылок
		int lastindex = 0;
		boolean endwithlink = false;
		for (Link link : extractUrls(toBc)) {
			String[] sp = toBc.split(link.getDest());
			try {
				msgblock.add(U.simple(sp[0], hover, suggest));
			} catch (ArrayIndexOutOfBoundsException ignored) {
			}
			msgblock.add(link);
			if (sp.length < 2) endwithlink = true;
			else lastindex = toBc.indexOf(sp[1]);
		}
		if (!endwithlink) {
			String lastpart = toBc.substring(lastindex);
			if (!lastpart.equals("")) msgblock.add(U.simple(lastpart, hover, suggest));
		}
		U.bclist(msgblock);
	}
	
	public static String decorateUrl(String b) {
		StringBuilder c = new StringBuilder("§n");
		boolean d = false;
		boolean e = false;
		for (char f : b.toCharArray()) {
			if (d && e && f == '/') break;
			else if (d && f == '/') e = true;
			else if (f == '/') d = true;
			else if (d && e) c.append(f);
		}
		return c.append("§r").toString();
	}
	
	
	private static Pattern pattern = Pattern.compile(
			"((https?):((\\/\\/)|(\\\\))+[\\w\\d:#@%\\/;$()~_?\\+-=\\\\\\.&]*)", Pattern.CASE_INSENSITIVE);
	
	private static List<Link> extractUrls(String text) {
		List<Link> containedUrls = new ArrayList<>();
		Matcher urlMatcher = pattern.matcher(text);
		while (urlMatcher.find()) containedUrls.add(new Link(text.substring(urlMatcher.start(0), urlMatcher.end(0))));
		return containedUrls;
	}
	
	private static String time() {
		Date date = new Date(new Date().getTime() + 7200000);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(date);
	}
	
	public static void disable() {
		off = true;
	}
	
	public static class Link {
		private final String dest;
		
		public Link(String dest) {
			this.dest = dest;
		}
		
		public String getDest() {
			return dest;
		}
	}
}
