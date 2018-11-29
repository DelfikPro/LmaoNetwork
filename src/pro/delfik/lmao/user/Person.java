package pro.delfik.lmao.user;

import implario.net.packet.PacketMoney;
import implario.util.debug.UserInfo;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import pro.delfik.lmao.Connect;
import pro.delfik.lmao.outward.item.I;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import implario.util.Rank;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Person {
	
	public static Person load(Player p, boolean auth, UserInfo info) {
		if (get(p) != null) return null;
		return new Person(p, auth, info);
	}
	
	public static void unload(String name) {
		Person p = Person.get(name);
		if (p == null) return;
		p.remove();
	}

	private static boolean playerCleanUpEnabled = true;
	public static void disablePlayerCleanUp() {
		playerCleanUpEnabled = false;
	}
	
	private static final HashMap<String, Person> names = new HashMap<>();
	public static final Set<String> authed = new HashSet<>();
	
	private final CraftPlayer handle;
	private UserInfo info;
	private boolean auth = false;
	private final String name;
	private volatile VanishInfo vanish = null;

	private boolean online = true;
	
	public Person(Player handle, boolean auth, UserInfo info) {
		this.handle = (CraftPlayer) handle;
		this.name = handle.getName();
		this.info = info;
		if (info.darkTheme) handle.setPlayerTime(15000, false);
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
		VanishInfo v = vanish;
		vanish = enable ? new VanishInfo(getLocation(), getGameMode()) : null;
		if (enable) {
			setGameMode(GameMode.SPECTATOR);
		} else {
			setGameMode(v.getGameMode());
			teleport(v.getLocation());
			v.disable();
		}
		return enable;
	}
	
	public void sendTitle(String title) {
		handle.getHandle().playerConnection.sendPacket(
				new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + title + "\"," + "\"color\":\"white\"}"))
		);
	}

	public void sendActionBar(String text) {
		handle.getHandle().playerConnection.sendPacket(
				new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2)
		);
	}

	public void clearArrows() {
		handle.getHandle().getDataWatcher().watch(9, (byte) 0);
	}
	
	public void sendSubtitle(String subtitle) {
		handle.getHandle().playerConnection.sendPacket(
				new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + subtitle + "\"," +
																						  "\"color\":\"white\"}"))
		);
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Person && ((Person) o).getName().hashCode() == name.hashCode();
	}
	
	public void remove() {
		names.remove(name.toLowerCase());
		online = false;
		authed.remove(name);
		if (playerCleanUpEnabled) I.delay(() -> cleanPlayerData(getHandle().getUniqueId().toString()), 4);
	}
	
	// Очистка мусорного файла с местоположением и инвентарём игрока
	private void cleanPlayerData(String uuid) {
		try {
			for (World loadedWorld : Bukkit.getWorlds()) {
				File playerdata = new File(loadedWorld.getName() + File.separator + "playerdata" + File.separator + uuid + ".dat");
				if (playerdata.exists()) playerdata.delete();
				File stats = new File(loadedWorld.getName() + File.separator + "stats" + File.separator + uuid + ".json");
				if (stats.exists()) stats.delete();
			}
		} catch (Throwable ignored) {}
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
	
	public int getMoney() {
		return info.money;
	}
	
	public long getOnlineTime() {
		return info.online;
	}
	
	public Rank getRank() {
		return info.rank;
	}
	
	public boolean hasRank(Rank rank) {
		return getRank().ordinal() <= rank.ordinal();
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
		this.info.rank = rank;
		getHandle().setDisplayName(rank.getPrefix() + "§7" + name);
	}
	
	public static boolean online(String arg) {
		return names.containsKey(arg.toLowerCase());
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public void msg(TextComponent text) {
		handle.spigot().sendMessage(text);
	}

	public void setDarkTheme(boolean dark) {
		info.darkTheme = dark;
		handle.setPlayerTime(dark ? 15000 : 5000, false);
	}

	public void earn(int money) {
		Connect.send(new PacketMoney(money, name));
	}
}
