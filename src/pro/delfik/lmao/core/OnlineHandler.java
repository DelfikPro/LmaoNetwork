package pro.delfik.lmao.core;

import lib.util.ParticleEffect;
import net.minecraft.server.v1_8_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pro.delfik.lmao.core.connection.database.ServerIO;
import pro.delfik.lmao.chat.TabList;
import pro.delfik.lmao.permissions.Core;
import pro.delfik.util.Rank;

public class OnlineHandler implements Listener {
	
	public static float angle;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Person p = new Person(e.getPlayer());
		Rank rank = User.getUser(e.getPlayer().getName()).getRank();
		p.setDisplayName(rank.getPrefix() + rank.getNameColor() + p.getName());
		TabList.send(e.getPlayer());
	}
	
	
	@EventHandler
	public void event(PlayerPreLoginEvent event) {
		if (Bukkit.getPlayer(event.getName()) != null) event.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, "§cТы шо, самый умный?)");
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoinLow(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String nick = player.getName();
		User user = User.getUser(nick);
		if (user == null) {
			player.kickPlayer("Чота тебя нету");
			return;
		}
		Boolean b = ServerIO.connect("getauth " + nick + " " + Bukkit.getMotd()).equals("true");
		if (b) return;
		Core.players.add(nick);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Person.get(e.getPlayer()).remove();
	}
}
