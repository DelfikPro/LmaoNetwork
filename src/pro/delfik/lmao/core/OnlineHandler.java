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
import pro.delfik.lmao.permissions.Rank;

public class OnlineHandler implements Listener {
	
	public static float angle;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Person p = new Person(e.getPlayer());
		Rank rank = User.getUser(e.getPlayer().getName()).getRank();
		p.setDisplayName(rank.getPrefix() + rank.getNameColor() + p.getName());
		rank.getTeam().addPlayer(p.getHandle());
		TabList.send(e.getPlayer());
		if (e.getPlayer().getName().equals("Desperated")) {
			Person.online().forEach(person -> {
				person.getHandle().playSound(person.getLocation(), Sound.WITHER_SPAWN, 1, 1);
			});
			new BukkitRunnable() {
				@Override
				public void run() {
					if (!p.exists()) {
						Person.online().forEach(a -> a.getHandle().kickPlayer("\"§dНикто тебе не поверит.\"\n§7 §7 §7 §7 §7 §7 §7 §7 §7 §7§o- Desperated."));
						cancel();
					}
					Location l = e.getPlayer().getLocation();
					double x = l.getX() + Math.cos(angle);
					double y1 = l.getY() + (0.7 * Math.sin(angle)) + 0.5;
					double y2 = l.getY() + (-0.7 * Math.sin(angle)) + 0.5;
					double z = l.getZ() + Math.sin(angle);
					Location loc1 = new Location(e.getPlayer().getWorld(), x, y1, z);
					Location loc2 = new Location(e.getPlayer().getWorld(), x, y2, z);
					ParticleEffect.display(EnumParticle.SUSPENDED_DEPTH, 0.1f, 0.1f, 0.1f, 0.01f, 25, loc1, 200);
					ParticleEffect.display(EnumParticle.SUSPENDED_DEPTH, 0.1f, 0.1f, 0.1f, 0.01f, 25, loc2, 200);
					angle = angle + 0.4f;
					if (angle >= 360) angle = 0;
				}
			}.runTaskTimer(Lmao.plugin, 1, 1);
		}
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
			if (!User.loadUser(nick)) return;
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
