package pro.delfik.lmao.ev;

import implario.net.packet.PacketUser;
import implario.util.Rank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.TimedMap;

public class EvJoin implements Listener{
	public static final TimedMap<String, PacketUser> waitingPackets = new TimedMap<>(10);

	@EventHandler(priority = EventPriority.LOWEST)
	public void eventLow(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String nick = player.getName();
		PacketUser packet = waitingPackets.get(nick.toLowerCase());
		if (packet == null) {
			player.kickPlayer("§cПревышено время ожидания.");
			return;
		}
		Person person = Person.load(player, packet.isAuthorized(), packet.getUserInfo());
		if (packet.isAuthorized()) person.auth();
	}

	@EventHandler
	public void event(PlayerJoinEvent event) {
		Person person = Person.get(event.getPlayer());
		Rank rank = person.getRank();
		person.setDisplayName(rank.getPrefix() + "§7" + person.getName());
	}
}
