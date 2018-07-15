//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pro.delfik.lmao.permissions;

import com.mysql.jdbc.Buffer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import pro.delfik.lmao.anticheat.AntiClicker;
import pro.delfik.lmao.core.Init;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.core.User;
import pro.delfik.lmao.core.connection.database.ServerIO;
import pro.delfik.lmao.core.connection.database.io.Helper;
import pro.delfik.lmao.core.connection.database.io.Reader;
import pro.delfik.lmao.core.connection.database.io.Writer;
import pro.delfik.lmao.core.connection.handle.SocketEvent;
import pro.delfik.util.CryptoUtils;
import pro.delfik.util.Scheduler;

import java.util.ArrayList;

public final class Core implements Runnable {
	public static final String prefix = "LMAO §e> §c";
	public static volatile ArrayList<String> players = new ArrayList<>();
	
	private static void classLoader() {
		Helper.class.getCanonicalName();
		Reader.class.getCanonicalName();
		Writer.class.getCanonicalName();
		SocketEvent.class.getCanonicalName();
		CryptoUtils.class.getCanonicalName();
		ServerIO.class.getCanonicalName();
		User.class.getCanonicalName();
	}
	
	public static void init() {
		classLoader();
		User.loadUser("CONSOLE");
		Bukkit.broadcastMessage("§cInitializing socketlistener...");
		new Thread(new Core()).start();
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
		Scheduler.addTask(new Scheduler.Task(10) {
			@Override
			public void run() {
				for (String s : players.toArray(new String[]{}))
					if(ServerIO.connect("getauth " + s + " " + Bukkit.getMotd()).equals("true")) players.remove(s);
			}
		});
	}
	
	public static void disable() {
		ServerIO.connect("remport " + Bukkit.getMotd());
		ServerIO.connect("remClaster " + Bukkit.getMotd());
		SocketEvent.close();
		AntiClicker.isStart = false;
	}
	
	@Override
	public void run() {
		SocketEvent.listening();
	}
}
