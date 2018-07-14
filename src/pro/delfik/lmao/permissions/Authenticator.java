package pro.delfik.lmao.permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pro.delfik.lmao.core.User;
import pro.delfik.lmao.core.connection.database.ServerIO;

public class Authenticator implements Listener {
	
	public static boolean isAuthorized(Player player) {
		if (player == null) return false;
		String name = player.getName();
		return Core.players.contains(name);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void event(BlockBreakEvent event) {
		if (isAuthorized(event.getPlayer()))
			event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void event(BlockPlaceEvent event) {
		if (isAuthorized(event.getPlayer()))
			event.setCancelled(true);
	}
	
	//Inventory
	@EventHandler(priority = EventPriority.LOW)
	public void event(InventoryClickEvent event) {
		if (isAuthorized((Player) event.getView().getPlayer())) event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void event(InventoryOpenEvent event) {
		if (isAuthorized((Player) event.getView().getPlayer())) event.setCancelled(true);
	}
	
	//Player
	
	@EventHandler
	public void event(PlayerInteractEvent e) {
		if (isAuthorized(e.getPlayer())) {
			e.setCancelled(true);
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
				e.getPlayer().sendMessage("§cВы не вошли в игру.");
		}
	}
	
	@EventHandler
	public void event(PlayerQuitEvent event) {
		String nick = event.getPlayer().getName();
		User.unloadUser(nick);
		ServerIO.connect("remplayer " + nick);
		Core.players.remove(nick);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void event(AsyncPlayerChatEvent event) {
		if (isAuthorized(event.getPlayer())) {
			event.getPlayer().sendMessage("§cВы не вошли в игру.");
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		if (isAuthorized(e.getPlayer())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cВы не вошли в игру.");
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void event(PlayerDropItemEvent event) {
		if (isAuthorized(event.getPlayer())) event.setCancelled(true);
	}
	
}
