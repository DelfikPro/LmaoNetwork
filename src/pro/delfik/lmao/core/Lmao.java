package pro.delfik.lmao.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pro.delfik.lmao.core.connection.database.Database;
import pro.delfik.lmao.outward.gui.GUILoading;
import pro.delfik.lmao.permissions.Core;
import implario.net.Packet;

public class Lmao extends JavaPlugin {
	
	public static Lmao plugin;
	private static final String prefix = "LMAO §e> §c";
	public static boolean noPhysics = true;

	public static String p() {return prefix;}
	
	@Override
	public void onEnable() {
		plugin = this;
		Packet.init();
		Core.init();
		Init.r = new Registrar(this);
		Init.events();
		Init.commands();
		Database.enable();
		GUILoading.startLoop();
		if (Bukkit.getServer().getMotd().startsWith("TEST_")) Person.disablePlayerCleanUp();
	}
	
	@Override
	public void onDisable() {
		Bukkit.broadcastMessage("§c§lСервер перезагружается. Все игры прерваны.");
		Core.disable();
	}
	
	public static String getColoredName(CommandSender sender) {
		return sender instanceof Player ? ((Player) sender).getDisplayName() : "§7Консоль сервера";
	}
}
