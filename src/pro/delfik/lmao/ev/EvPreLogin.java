package pro.delfik.lmao.ev;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pro.delfik.lmao.util.TimedMap;
import implario.net.packet.PacketUser;
import implario.util.Rank;

public class EvPreLogin implements Listener {
	@EventHandler
	public void event(PlayerPreLoginEvent event) {
		if(Bukkit.getPlayer(event.getName()) != null)
			event.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, "§cТы шо, самый умный?)");
	}
}

