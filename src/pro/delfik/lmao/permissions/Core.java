//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pro.delfik.lmao.permissions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import pro.delfik.lmao.anticheat.AntiClicker;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.core.connection.Connect;
import pro.delfik.lmao.core.connection.PacketEvent;
import pro.delfik.util.CryptoUtils;
import pro.delfik.util.Scheduler;

import java.util.ArrayList;

public final class Core {
	public static final String prefix = "LMAO §e> §c";
	public static volatile ArrayList<String> players = new ArrayList<>();
	
	private static void classLoader() {
		PacketEvent.class.getCanonicalName();
		CryptoUtils.class.getCanonicalName();
		Person.class.getCanonicalName();
	}
	
	public static void init() {
		classLoader();
		Bukkit.broadcastMessage("§cInitializing socketlistener...");
		Connect.init();
		Bukkit.broadcastMessage("§aSocketlistener-state: ACTIVE");
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
		AntiClicker.isStart = false;
	}
}
