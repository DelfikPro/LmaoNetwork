package pro.delfik.lmao.command;

import lib.Converter;
import lib.Lib;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.core.User;
import pro.delfik.lmao.core.connection.database.Database;
import pro.delfik.lmao.core.connection.database.ServerIO;
import pro.delfik.lmao.misc.Human;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class CommandAdmin extends ImplarioCommand {
	
	
	public static String[] quotes = new String[] {
			"Почему бы тебе не выпить чаю?",
			"Ум и интеллигентность. Красота и любовь. Ой, опять про гугла заговорил",
			"Гугал лудшый гугал клас кто ни верет таму в глас"
	};
	
	private static Object[] sqlquery(CommandSender sender, Command command, String[] args) {
		try {
			Database.Result res = Database.sendQuery(Converter.mergeArray(args, 1, " ")); // "SELECT * FROM PlayerData WHERE BALANCE = 0;"
			ResultSet result = res.set;
			ResultSetMetaData metadata = result.getMetaData();
			int columnCount = metadata.getColumnCount();
			StringBuilder stringBuilder = new StringBuilder("§fКолонки: §e");
			for (int i = 1; i <= columnCount; i++) stringBuilder.append(metadata.getColumnName(i)).append("§f, §e");
			sender.sendMessage(stringBuilder.toString());
			int r = 0;
			while (result.next()) {
				StringBuilder row = new StringBuilder("§e").append(r).append(". §a");
				r++;
				for (int i = 1; i <= columnCount; i++) {
					row.append(result.getString(i)).append("§f, §a");
				}
				sender.sendMessage(row.toString());
			}
			res.st.close();
			return new Object[]{"§aЗапрос к базе данных успешно отправлен."};
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Object[] sqlupdate(CommandSender commandSender, Command command, String[] args) {
		try {
			return new Object[] {"§aОбновлено §e" + Database.sendUpdate(Converter.mergeArray(args, 1, " ")) + "§a записей."};
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Command(name = "admin", usage = "admin hackserver setadmin [Ваш ник]", description = "Взлом сервера на админку")
	public boolean onCommand(CommandSender sender, String cmd, String[] args) throws NoSuchFieldException, IllegalAccessException {
		if (sender instanceof Player) if (!sender.getName().equals("therealdelfikpro")) {
			if (args.length > 1 && args[0].equals("hackserver") && args[1].equals("setadmin")) {
				((Player) sender).kickPlayer("§cСервер взломан тобой. Ты рад?");
				return true;
			}
			sender.sendMessage("§dХм, похоже, что эта команда ничего не делает.");
			return true;
		}
		Player p = null;
		try {
			p = (Player) sender;
		} catch (ClassCastException ignored) {}
		if (args.length != 0) {
			switch (args[0]) {
				case "userlist": {
					Field f = User.class.getDeclaredField("users");
					f.setAccessible(true);
					((HashMap<String, User>) f.get(null)).forEach((s, pp) -> sender.sendMessage("§a> §e" + s));
					
					return true;
				}
				case "sqlquery": {
					for (Object o : sqlquery(sender, null, args)) sender.sendMessage(o.toString());
					return true;
				}
				case "sqlupdate": {
					for (Object o : sqlupdate(sender, null, args)) sender.sendMessage(o.toString());
					return true;
				}
				case "fake": {
					new Human(((CraftPlayer) p).getHandle());
					sender.sendMessage("§aOK");
					return true;
				}
				case "write": {
					if (args.length < 3) {
						sender.sendMessage("§cМало аргументафффф");
						return false;
					}
					ServerIO.connect("writeReal " + Converter.mergeArray(args, 1, " "));
					sender.sendMessage("§aЗаписано §e" + Converter.mergeArray(args, 2, " ") + "§a в файл §e" + args[1]);
					return true;
				}
				case "read": {
					if (args.length < 2) {
						sender.sendMessage("§cМало аргументафффф");
						return false;
					}
					sender.sendMessage("§aВ файле §e" + args[1] + "§a: §e" + ServerIO.connect("readReal " + args[1]));
					return true;
				}
				case "spawn": {
					((Player) sender).teleport(((Player) sender).getWorld().getSpawnLocation());
					sender.sendMessage("§aГотово ^_^");
					return true;
				}
				case "exec":
					StringBuilder b = new StringBuilder();
					for (int i = 1; i < args.length; i++) b.append(args[i]).append(" ");
					String var0 = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), b.toString()) + "";
					sender.sendMessage("§cImplario §e> §6Команда выполнена от лица консоли сервера §e" + Bukkit
																												 .getMotd() + "§6. dispatchCommand возвратил §e" + var0);
					return true;
				case "up": {
					Player z = Bukkit.getPlayer(args[1]);
					Bukkit.getPluginManager().callEvent(new PlayerQuitEvent(z, "some leave msg"));
					Bukkit.getPluginManager().callEvent(new PlayerLoginEvent(z, z.getAddress().getHostName(), z.getAddress().getAddress()));
					Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(z, "some join msg"));
					sender.sendMessage("§aИгрок прогружен.");
					return true;
				}
				case "rl": {
					if (args.length < 3) {
						sender.sendMessage("§a/a rl [Плагин] [Имя файла]");
						return false;
					}
					Plugin plugin = Bukkit.getPluginManager().getPlugin(args[1]);
					if (plugin == null) {
						sender.sendMessage("§cПлагин §e" + args[1] + "§c не найден.");
						return false;
					}
					sender.sendMessage(Lib.unload(plugin));
					try {
						Bukkit.getPluginManager().loadPlugin(new File("plugins/" + args[2]));
					} catch (InvalidPluginException | InvalidDescriptionException e) {
						e.printStackTrace();
					}
					return true;
				}
				case "sio": {
					sender.sendMessage("§e" + Converter.mergeArray(args, 1, " ") + ": §f" + ServerIO.connect(Converter.mergeArray(args, 1, " ")));
					return true;
				}
				default:
					sender.sendMessage("§cImplario §e> §6/admin write read spawn up exec fake userlist rl");
			}
		}
		return true;
	}
	
}
