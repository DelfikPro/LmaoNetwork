package pro.delfik.lmao.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Vec5d implements Vec {
	private final double x, y, z;
	private final float yaw, pitch;
	
	public Vec5d(double x, double y, double z, double yaw, double pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = (float) yaw;
		this.pitch = (float) pitch;
	}

	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}

	@Override
	public double z() {
		return z;
	}

	@Override
	public float yaw() {
		return yaw;
	}

	@Override
	public float pitch() {
		return pitch;
	}
	
	@Override
	public Location toLocation(World w) {
		return new Location(w, x, y, z, yaw, pitch);
	}
}
