package pro.delfik.lmao.anticheat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pro.delfik.lmao.core.Person;
import pro.delfik.util.Rank;

import java.util.HashMap;

public class AntiClicker extends Thread implements Listener {
	
	public static boolean isStart = true;
	
	public AntiClicker() {
		start();
	}
	
	private volatile HashMap<String, Integer> damages = new HashMap<>();
	
	@EventHandler
	public void event(PlayerInteractEvent event) {
		if (event.getAction() == Action.PHYSICAL) return;
		if(!isStart)return;
		Player player = event.getPlayer();
		String nick = player.getName();
		Integer damage = damages.get(nick);
		if (damage == null) damage = 0;
		damage++;
		damages.put(nick, damage);
		if (damage > 9) {
			if (Person.get(nick).getRank() == Rank.DEV) return;
			event.setCancelled(true);
		}
		if (damage > 30) player.kickPlayer("§dПо-моему тыкать по мышке 30 раз в секунду как-то нечестно, тебе не кажется?");
	}
	
	@Override
	public void run() {
		while (true) {
			if (!isStart) return;
			try {
				sleep(1000);
			} catch (InterruptedException ignored) {}
			damages = new HashMap<>();
		}
	}
}
