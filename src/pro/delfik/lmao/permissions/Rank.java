//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pro.delfik.lmao.permissions;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public enum Rank {
	
	DEV("§7<§3§lDev§7> ", "§3", "Разработчик"),
	ADMIN("§7<§c§lA§7> ", "§c", "Админ"),
	LMAO("§7<§d§lLMAO§7> ", "§d", "LMAO"),
	IMPLARIO("§7<§8§lImplario§7> ", "§8", "Implario"),
	KURATOR("§7<§6§lКуратор§7> ", "§6", "Куратор"),
	ULTIBUILDER("§7<§d§lАрхитектор§7> ", "§d", "Архитектор"),
	SPONSOR("§7<§b§lСпонсор§7> ", "§b", "Спонсор"),
	WARDEN("§4<§e§lМодер§4> ", "§e", "Пр. Модератор"),
	MODER("§7<§e§lМодер§7> ", "§e", "Модератор"),
	RECRUIT("§7<§e§lСтажёр§7> ", "§e", "Модератор-стажёр"),
	BUILDER("§7<§2§lБилдер§7> ", "§2", "Билдер"),
	YOUTUBE("§7<§cYou§fTube§7> ", "§c", "Ютубер"),
	TESTER("§7<§a§lT§7> ", "§a", "Тестер"),
	VIP("§7<§a§lV§7> ", "§a", "VIP"),
	PLAYER("", "", "Игрок");
	
	public static final HashMap<Character, Rank> byChar = new HashMap<>();
	static {for (Rank rank : values()) byChar.put(rank.name().charAt(0), rank);}
	
	private final String name;
	private String prefix;
	private String nameColor;
	private Team team;
	
	Rank(String prefix, String nameColor, String name) {
		this.name = name;
		this.prefix = prefix;
		this.nameColor = nameColor;
		Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
		Team t = sb.getTeam(name);
		if (t != null) t.unregister();
		this.team = sb.registerNewTeam(name);
		this.team.setPrefix(nameColor);
	}
	
	public Team getTeam() {
		return team;
	}
	
	public String getNameColor() {
		return nameColor;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getName() {
		return name;
	}
	
	public static Rank decode(String line) {
		if (line == null || line.length() == 0) return PLAYER;
		Rank r = byChar.get(line.charAt(0));
		return r == null ? Rank.PLAYER : r;
	}
	
	public String toString() {
		return String.valueOf(name().charAt(0));
	}
}
