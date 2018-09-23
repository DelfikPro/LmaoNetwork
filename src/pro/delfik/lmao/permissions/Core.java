//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pro.delfik.lmao.permissions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import pro.delfik.lmao.ev.EvInteract;
import pro.delfik.lmao.Lmao;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.Connect;
import pro.delfik.lmao.ev.added.PacketEvent;
import implario.util.CryptoUtils;
import implario.util.Scheduler;

public final class Core {
	
	private static void classLoader() {
		PacketEvent.class.getCanonicalName();
		CryptoUtils.class.getCanonicalName();
		Person.class.getCanonicalName();
	}
	
	public static void init() {
		classLoader();
		Bukkit.broadcastMessage("§6Подключаемся к прокси-серверу...");
		try {
			Connect.init();
		} catch (Exception ex) {
			Bukkit.broadcastMessage("§c§lНе удалось подключится к прокси. Сервер не сможет работать, как надо.");
		}
		Bukkit.broadcastMessage("§aКластер успешно присоединился к прокси и готов к работе.");
		Scheduler.init();
		Bukkit.getScheduler().runTaskLater(Lmao.plugin, () -> {
			for (Player p : Bukkit.getOnlinePlayers())
				try {
					Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(p, ""));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}, 40L);
	}
	
	public static void disable() {
		Connect.close();
		Scheduler.kill();
		EvInteract.isStart = false;
	}
}
