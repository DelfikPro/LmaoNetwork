package pro.delfik.lmao.ev;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EvCmdPreprocess implements Listener{
	@EventHandler
	public void event(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String command = event.getMessage().split(" ")[0];
		boolean op = player.isOp();
		if(command.contains(":") && (!op || command.substring(11).equals("op"))) event.setCancelled(true);
	}
}
