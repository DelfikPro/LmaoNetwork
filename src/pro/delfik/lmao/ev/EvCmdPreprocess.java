package pro.delfik.lmao.ev;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EvCmdPreprocess implements Listener{
	@EventHandler
	public void event(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().split(" ")[0].contains(":") && !event.getPlayer().isOp()) event.setCancelled(true);
	}
}
