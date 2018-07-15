package pro.delfik.lmao.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pro.delfik.lmao.core.connection.database.ServerIO;
import pro.delfik.util.Rank;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class User {
	private static final HashMap<String, User> users = new HashMap<>();
	
	private Rank rank;
	private final String nick;
	
	private User(Rank rank, String nick) {
		this.rank = rank;
		this.nick = nick;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(nick);
	}
	
	public CommandSender getSender() {
		Player player = getPlayer();
		return player == null ? Bukkit.getConsoleSender() : player;
	}
	
	public boolean isPlayer() {
		return getPlayer() != null;
	}
	
	public void sendMessage(String message) {
		sendMessage(message, Lmao.p());
	}
	
	public void sendMessage(String message, String prefix) {
		getSender().sendMessage(prefix + message);
	}
	
	@Override
	public String toString() {
		return nick;
	}
	
	public static User getUser(String nick) {
		return users.get(nick.toLowerCase());
	}
	
	public static boolean loadUser(String nick) {
		if (getUser(nick) != null) return false;
		Rank rank;
		String user = ServerIO.pex(nick + " " + Bukkit.getMotd());
		if (user.equals("n")) {
			Bukkit.getScheduler().runTask(Lmao.plugin, () -> {
				try {
					Bukkit.getPlayer(nick).kickPlayer("§cПроизошла какая-то неведомая херня. Похоже, вы слишком быстро меняли сервер.\n" +
						  "§cМы извиняемся за это. Пожалуйста, просто перезайдите на сервер.\n" +
						  "§6TIMESTAMP: §e" + new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy").format(new Date()) + "\n" +
						  "§6CLUSTER: §e" + Bukkit.getMotd() + "\n§6PLAYERNAME: §e" + nick +
						  "\n§6DESCRIPTION: §e\'Прокси-сервер не отправил данных о вашем заходе на Баккит-сервер.\'");
				} catch (Exception ex) {
					users.put(nick.toLowerCase(), new User(Rank.PLAYER, nick));
				}
			});
			return false;
		}
		rank = Rank.decode(user);
		users.put(nick.toLowerCase(), new User(rank, nick));
		return true;
	}
	
	public static void unloadUser(String nick) {
		users.remove(nick.toLowerCase());
	}
}
