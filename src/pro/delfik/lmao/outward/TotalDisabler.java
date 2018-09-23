package pro.delfik.lmao.outward;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.Lmao;

public class TotalDisabler implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Lmao.plugin, e.getEntity().spigot()::respawn, 18L);
	}
	
	@EventHandler
	public void noWeaponBreakDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			ItemStack item = ((Player)event.getDamager()).getItemInHand();
			switch (item.getType()) {
				case IRON_SWORD:
				case DIAMOND_SWORD:
				case GOLD_SWORD:
				case STONE_SWORD:
				case WOOD_SWORD: {
					item.setDurability((short) 0);
				}
			}
		} else if (event.getEntity() instanceof Player) {
			ItemStack[] armor = ((Player)event.getEntity()).getInventory().getArmorContents();
			for (ItemStack i : armor) {
				i.setDurability((short) 0);
			}
			((Player) event.getEntity()).getInventory().setArmorContents(armor);
		}
	}
	
	@EventHandler
	public void noWeaponBreakDamage(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) event.getBow().setDurability((short) 0);
	}
	
	@EventHandler
	public void disableWeather(WeatherChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onSign(SignChangeEvent e) {
		Sign s = (Sign) e.getBlock().getState();
		s.setLine(0, e.getLine(0).replace('&', '§'));
		s.setLine(1, e.getLine(1).replace('&', '§'));
		s.setLine(2, e.getLine(2).replace('&', '§'));
		s.setLine(3, e.getLine(3).replace('&', '§'));
	}

//	@EventHandler
//	public void onCreatureSpawn(CreatureSpawnEvent event) {
//		if (!Lmao.noPhysics) return;
//		if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
//			event.setCancelled(true);
//		}
//	}
	
	@EventHandler
	public void onBlockGrow(BlockGrowEvent event) {
		if (Lmao.noPhysics) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		if (Lmao.noPhysics) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockFade(BlockFadeEvent event) {
		if (Lmao.noPhysics) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockFade(BlockPhysicsEvent e) {
		if (Lmao.noPhysics) e.setCancelled(true);
	}
//
//	@EventHandler
//	public void onWeather(WeatherChangeEvent event) {
//		if (!Lib.weatherDisabled) return;
//		WorldServer world = ((CraftWorld) event.getWorld()).getHandle();
//		world.worldData.setStorm(false);
//		world.worldData.setThundering(false);
//		world.worldData.setThunderDuration(0);
//	}
	
	@EventHandler
	public void onBlockForm(BlockFormEvent event) {
		if (Lmao.noPhysics) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (Lmao.noPhysics) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockSpread(BlockSpreadEvent event) {
		if (Lmao.noPhysics) event.setCancelled(true);
	}
	
	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent event) {
		if (Lmao.noPhysics) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		if (Lmao.noPhysics) event.setCancelled(true);
	}
	
	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent event) {
		if (event.getItem().getType() == Material.POTION) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(Lmao.plugin, () -> event.getPlayer().setItemInHand(new ItemStack(Material.AIR)), 1);
		}
	}
	
	@EventHandler
	public void signEditor(SignChangeEvent e) {
		for (int i = 0; i < 4; i++) e.setLine(i, e.getLine(i).replace('&', '§'));
	}
}
