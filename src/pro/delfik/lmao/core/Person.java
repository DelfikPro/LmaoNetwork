package pro.delfik.lmao.core;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import pro.delfik.lmao.misc.Human;
import pro.delfik.lmao.modules.VanishInfo;
import pro.delfik.util.Rank;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Person {
	
	
	public static Person load(Player p, Rank rank, boolean auth) {
		if (get(p) != null) return null;
		return new Person(p, rank, auth);
	}
	
	public static void unload(String name) {
		Person p = Person.get(name);
		if (p == null) return;
		p.remove();
	}

	private static final HashMap<String, Person> names = new HashMap<>();
	public static final Set<String> authed = new HashSet<>();
	
	private final CraftPlayer handle;
	private Rank rank;
	private boolean auth = false;
	private final String name;
	private volatile VanishInfo vanish = null;
	
	private boolean online = true;
	
	public Person(Player handle, Rank rank, boolean auth) {
		this.handle = (CraftPlayer) handle;
		this.name = handle.getName();
		this.rank = rank;
		names.put(name.toLowerCase(), this);
		if (auth) auth();
	}
	
	public boolean isAuth() {
		return auth;
	}
	
	public void auth() {
		this.auth = true;
		authed.add(name);
	}
	
	public static Collection<Person> online() {
		return names.values();
	}
	
	public static Person get(String name) {
		return names.get(name.toLowerCase());
	}
	
	public static Person get(CommandSender p) {
		return get(p.getName());
	}
	
	public boolean vanish() {
		boolean enable = vanish == null;
		final VanishInfo v = vanish;
		final Person p = this;
		vanish = enable ? new VanishInfo(getLocation(), getGameMode(), new Human(handle.getHandle())) : null;
		if (enable) {
			setGameMode(GameMode.SPECTATOR);
		} else {
			setGameMode(v.getGameMode());
			teleport(v.getLocation());
			v.getDisplay().remove();
			v.disable();
		}
		return enable;
	}
	
	public void sendTitle(String title) {
		handle.getHandle().playerConnection.sendPacket(
				new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + title + "\"," + "\"color\":\"white\"}"))
		);
	}
	
	public void sendSubtitle(String subtitle) {
		handle.getHandle().playerConnection.sendPacket(
				new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + subtitle + "\"," +
																						  "\"color\":\"white\"}"))
		);
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Person && ((Person) o).getName().hashCode() == name.hashCode();
	}
	
	public void remove() {
		names.remove(name.toLowerCase());
		online = false;
		authed.remove(name);
		if (vanish != null) vanish.getDisplay().remove();
	}
	
	public Location getLocation() {
		return handle.getLocation();
	}
	
	public boolean exists() {
		return online;
	}
	
	public boolean setFlight(boolean flight) {
		handle.setAllowFlight(flight);
		return flight;
	}
	
	public boolean getFlight() {
		return handle.getAllowFlight();
	}
	
	public boolean toggleFlight() {
		return setFlight(!getFlight());
	}
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return handle.getDisplayName();
	}
	
	public void setDisplayName(String name) {
		handle.setDisplayName(name);
	}
	
	public void sendMessage(String message) {
		handle.sendMessage(message);
	}
	
	public Player getHandle() {
		return handle;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public boolean hasRank(Rank rank) {
		return this.rank.ordinal() <= rank.ordinal();
	}
	
	public void setGameMode(GameMode mode) {
		handle.setGameMode(mode);
	}
	
	public boolean isVanish() {
		return vanish != null;
	}
	
	public void playSound(Sound sound, float volume, float pitch) {
		getHandle().playSound(getLocation(), sound, volume, pitch);
	}
	
	public void teleport(Location location) {
		getHandle().teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
	}
	
	public void teleport(Entity entity) {
		getHandle().teleport(entity, PlayerTeleportEvent.TeleportCause.PLUGIN);
	}
	
	public GameMode getGameMode() {
		return handle.getGameMode();
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public static boolean online(String arg) {
		return names.containsKey(arg.toLowerCase());
	}
}
