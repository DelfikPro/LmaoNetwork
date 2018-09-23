package pro.delfik.lmao.misc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.List;

public class Garpoon implements Listener {
	private static void pullEntityToLocation(Entity e, Location loc) {
		Location entityLoc = e.getLocation();
		e.setFallDistance(0);
		entityLoc.setY(entityLoc.getY() + 0.5D);
		e.teleport(entityLoc);

		double g = -0.08D;
		double d = loc.distance(entityLoc);
		double v_x = (1.0D + 0.07D * d) * (loc.getX() - entityLoc.getX()) / d;
		double v_y = (1.0D + 0.03D * d) * (loc.getY() - entityLoc.getY()) / d - 0.5D * g * d;
		double v_z = (1.0D + 0.07D * d) * (loc.getZ() - entityLoc.getZ()) / d;

		Vector v = e.getVelocity();
		v.setX(v_x);
		v.setY(v_y);
		v.setZ(v_z);
		e.setVelocity(v);
	}

	private static final List<String> pulled = new LinkedList<>();

	@EventHandler
	public void onPull(PlayerFishEvent e) {
		if (e.getState() == PlayerFishEvent.State.FISHING) return;
		if (!isBlockNearby(e.getHook().getLocation())) return;
		//User.getUser(e.getPlayer().getName()).getAntiFly().on();
		pullEntityToLocation(e.getPlayer(), e.getHook().getLocation());
		pulled.add(e.getPlayer().getName());
	}

	@EventHandler
	public void onPlayerPrizemlitsa(PlayerMoveEvent e) {
		if (!((Entity) e.getPlayer()).isOnGround()) return;
		if (!pulled.contains(e.getPlayer().getName())) return;
		//User.getUser(e.getPlayer().getName()).getAntiFly().off();
		pulled.remove(e.getPlayer().getName());
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.CAKE_BLOCK && !e.getPlayer().isSneaking()) {
			if (e.getClickedBlock().getData() == 6) e.getClickedBlock().setType(Material.AIR);
			else e.getClickedBlock().setData((byte) (e.getClickedBlock().getData() + 1));
		}
	}

	private static boolean isBlockNearby(Location loc) {
		Block b = loc.getBlock();
		for (BlockFace face : new BlockFace[] {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH,
				BlockFace.UP, BlockFace.WEST}) {
			if (b.getRelative(face).getType() != Material.AIR) return true;
		}
		return false;
	}
}
