package pro.delfik.lmao.outward;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import pro.delfik.lmao.outward.item.I;

public class Trampoline implements Listener {
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.PHYSICAL) return;
		if (e.getClickedBlock().getType() != Material.GOLD_PLATE) return;
		Player p = e.getPlayer();
		p.setVelocity(new Vector(0, 0.3, 0));
		p.playSound(p.getLocation(), Sound.HORSE_GALLOP, 1, 0.7f);
		I.delay(() -> {
			p.setVelocity(p.getLocation().getDirection().multiply(1.6f).setY(1.6));
			p.playSound(p.getLocation(), Sound.HORSE_GALLOP, 1, 0.7f);
		}, 2);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Location loc = e.getEntity().getLocation();
		loc.getWorld().playSound(loc, Sound.WOOD_CLICK, 1, 1);
		for (int i = 0; i < 10; i++) loc.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
	}
}
