package pro.delfik.lmao.misc;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class Invseer implements Listener {
	@EventHandler
	public void onInteractAtPlayer(PlayerInteractAtEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.PLAYER && e.getPlayer().getGameMode() == GameMode.SPECTATOR)
			e.getPlayer().openInventory(((Player) e.getRightClicked()).getInventory());
	}
}
