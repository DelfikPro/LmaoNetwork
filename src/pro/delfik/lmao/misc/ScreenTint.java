package pro.delfik.lmao.misc;

import net.minecraft.server.v1_8_R1.EnumWorldBorderAction;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorldBorder;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScreenTint {
	protected List<String> togglelist = new ArrayList<>();

	protected void sendBorder(Player p, int percentage) {
		setBorder(p, percentage);
		fadeBorder(p, percentage, 10);
	}

	protected void fadeBorder(Player p, int percentage, long time) {
		int dist = 55536 * percentage + 1300000;
		sendWorldBorderPacket(p, 0, 200000.0D, dist, 1000L * time + 4000L);
	}

	protected void removeBorder(Player p) {
		sendWorldBorderPacket(p, 0, 200000.0D, 200000.0D, 0L);
	}

	protected void setBorder(Player p, int percentage) {
		int dist = 55536 * percentage + 1300000;
		sendWorldBorderPacket(p, dist, 200000.0D, 200000.0D, 0L);
	}

	protected void sendWorldBorderPacket(Player p, int dist, double oldradius, double newradius, long delay) {
		try {
			CraftWorld world = ((CraftWorld) p.getWorld());
			CraftWorldBorder cwb = new CraftWorldBorder(world);
			cwb.setCenter(p.getLocation());
			cwb.setSize(newradius, delay);
			cwb.setWarningTime(15);
			cwb.setWarningDistance(dist);

			PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(world.getHandle().af(), EnumWorldBorderAction.INITIALIZE);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	protected int getPlayerHealth(Player p) {
		return (int) p.getHealth();
	}

	protected int getMaxPlayerHealth(Player p) {
		return (int) p.getMaxHealth();
	}

	protected int getPlayerMissingHearts(Player p) {
		return (int) p.getMaxHealth();
	}

	protected int getPlayerHealthPercentage(Player p) {
		int health = getPlayerHealth(p);
		int maxhealth = getMaxPlayerHealth(p);
		return Math.round(health * 100 / maxhealth);
	}

	protected void disablePlayerTint(Player p) {
		this.togglelist.add(p.getName());
	}

	protected void enablePlayerTint(Player p) {
		String pname = p.getName();
		if (this.togglelist.contains(pname)) {
			this.togglelist.remove(pname);
		}
	}

	protected void togglePlayerTint(Player p) {
		if (isTintEnabled(p)) {
			disablePlayerTint(p);
		} else {
			enablePlayerTint(p);
		}
	}

	protected boolean isTintEnabled(Player p) {
		return !this.togglelist.contains(p.getName());
	}
}

