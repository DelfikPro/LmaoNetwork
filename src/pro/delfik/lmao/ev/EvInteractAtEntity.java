package pro.delfik.lmao.ev;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EvInteractAtEntity implements Listener {
	@EventHandler
	public void event(PlayerInteractAtEntityEvent event) {
		if (event.getRightClicked().getType() == EntityType.PLAYER && event.getPlayer().getGameMode() == GameMode.SPECTATOR)
			event.getPlayer().openInventory(((Player) event.getRightClicked()).getInventory());
	}
}
