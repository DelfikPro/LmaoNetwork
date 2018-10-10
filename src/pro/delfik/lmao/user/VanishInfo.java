package pro.delfik.lmao.user;

import org.bukkit.GameMode;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class VanishInfo {
	
	public static final Set<VanishInfo> list = new HashSet<>();
	
	private final Location loc;
	private final GameMode gm;

	public VanishInfo(Location loc, GameMode gm) {
		this.loc = loc;
		this.gm = gm;
		list.add(this);
	}
	
	public GameMode getGameMode() {
		return gm;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void disable() {
		list.remove(this);
	}
}
