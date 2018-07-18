package pro.delfik.lmao.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pro.delfik.lmao.util.TimedMap;
import pro.delfik.net.packet.PacketUser;
import pro.delfik.util.Rank;

public class OnlineHandler implements Listener {
	
	public static final TimedMap<String, PacketUser> waitingPackets = new TimedMap<>(10);
	
	public static float angle;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Person p = Person.get(e.getPlayer());
		Rank rank = p.getRank();
		p.setDisplayName(rank.getPrefix() + rank.getNameColor() + p.getName());
	}
	
	@EventHandler
	public void event(PlayerPreLoginEvent event) {
		if (Bukkit.getPlayer(event.getName()) != null) event.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, "§cТы шо, самый умный?)");
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoinLow(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String nick = player.getName();
		PacketUser packet = waitingPackets.get(nick.toLowerCase());
		if (packet == null) {
			player.kickPlayer("§cПревышено время ожидания.");
			return;
		}
		Person person = Person.load(player, packet.getRank(), packet.isAuthorized());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Person.get(e.getPlayer()).remove();
	}
}
