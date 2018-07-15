package pro.delfik.lmao.core;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pro.delfik.lmao.core.connection.database.Database;
import pro.delfik.lmao.permissions.Core;
import pro.delfik.lmao.util.ServerType;

public class Lmao extends JavaPlugin {
	
	public static Lmao plugin;
	private static final String prefix = "LMAO §e> §c";
	public static ServerType server_type;
	
	public static String p() {return prefix;}
	
	@Override
	public void onEnable() {
		plugin = this;
		Core.init();
		Init.r = new Registrar(this);
		Init.events();
		Init.commands();
		Database.enable();
	}
	
	@Override
	public void onDisable() {
		Core.disable();
	}
	
	public static String getColoredName(CommandSender sender) {
		return sender instanceof Player ? ((Player) sender).getDisplayName() : "§7Консоль сервера";
	}
}
