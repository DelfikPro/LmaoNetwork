package pro.delfik.lmao.ev;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pro.delfik.lmao.user.Person;

public class EvLeave implements Listener{
	@EventHandler
	public void event(PlayerQuitEvent event) {
		Person person = Person.get(event.getPlayer().getName());
		if(person == null)return;
		person.remove();
	}
}
