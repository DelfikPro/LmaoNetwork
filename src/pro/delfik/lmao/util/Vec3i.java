package pro.delfik.lmao.util;

import __google_.util.ByteUnzip;
import __google_.util.ByteZip;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Три числа int в одном объекте.
 * @see Vec
 */
public class Vec3i implements Vec {
	public final int x, y, z;

	public Vec3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3i(double x, double y, double z){
		this((int)x, (int)y, (int)z);
	}

	public Vec3i(ByteUnzip unzip){
		this(unzip.getInt(), unzip.getInt(), unzip.getInt());
	}
	
	public static Vec3i fromLocation(Location location) {
		return new Vec3i(location.getBlockX(), location.getBlockY(), location.getBlockZ());
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
	public Location toLocation(World w) {
		return new Location(w, x + 0.5, y + 0.5, z + 0.5);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vec3i)) return false;
		Vec3i o = (Vec3i) obj;
		return o == this || (o.x == x && o.y == y && o.z == z);

	}
	
	@Override
	public int hashCode() {
		return x * 1000000 + y * 1000 + z;
	}

	public byte[] toByteArray() {
		return new ByteZip().add(x).add(y).add(z).build();
	}

	public static Vec3i read(byte[] array) {
		ByteUnzip u = new ByteUnzip(array);
		return new Vec3i(u.getInt(), u.getInt(), u.getInt());
	}

}
