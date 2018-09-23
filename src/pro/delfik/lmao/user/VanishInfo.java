package pro.delfik.lmao.user;

import org.bukkit.GameMode;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class VanishInfo {
	
	public static final Set<VanishInfo> list = new HashSet<>();
	
	private final Location loc;
	private final GameMode gm;
	private final Human h;
	
	public VanishInfo(Location loc, GameMode gm, Human h) {
		this.loc = loc;
		this.gm = gm;
		this.h = h;
		list.add(this);
	}
	
	public Human getDisplay() {
		return h;
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
