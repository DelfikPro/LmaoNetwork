package pro.delfik.lmao.ev;

import implario.util.Rank;
import implario.util.Scheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pro.delfik.lmao.user.Person;

import java.util.HashMap;

public class EvInteract implements Listener {
	public EvInteract() {
		Scheduler.addTask(new Scheduler.RunTask(10, () -> damages = new HashMap<>()));
	}
	
	@EventHandler
	public void event(PlayerInteractEvent event) {
		if (event.getAction() == Action.PHYSICAL) return;
		Player player = event.getPlayer();
		String nick = player.getName();
		if(antiClicker(player, nick))event.setCancelled(true);
	}

	private boolean antiClicker(Player player, String nick){
		if(!isStart)return false;
		Integer damage = damages.get(nick);
		if (damage == null) damage = 0;
		damage++;
		damages.put(nick, damage);
		if (damage > 9) {
			if (Person.get(nick).getRank() == Rank.DEV) return false;
			if (damage > 30) player.kickPlayer
					("§dПо-моему тыкать по мышке 30 раз в секунду как-то нечестно, тебе не кажется?");
			return true;
		}
		return false;
	}

	public static boolean isStart = true;

	private volatile HashMap<String, Integer> damages = new HashMap<>();
}
