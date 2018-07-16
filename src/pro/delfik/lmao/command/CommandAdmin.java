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
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.core.connection.database.Database;
import pro.delfik.lmao.misc.Human;
import pro.delfik.util.Rank;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CommandAdmin extends LmaoCommand {
	
	
	public static String[] quotes = new String[] {
			"Почему бы тебе не выпить чаю?",
			"Ум и интеллигентность. Красота и любовь. Ой, опять про гугла заговорил",
			"Гугал лудшый гугал клас кто ни верет таму в глас"
	};
	
	
	public CommandAdmin() {
		super("admin", Rank.PLAYER, "Взламать сервир нафег!!1");
	}
	
	
	private static Object[] sqlquery(CommandSender sender, String command, String[] args) {
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
	
	private static Object[] sqlupdate(CommandSender commandSender, String command, String[] args) {
		try {
			return new Object[] {"§aОбновлено §e" + Database.sendUpdate(Converter.mergeArray(args, 1, " ")) + "§a записей."};
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void run(CommandSender sender, String cmd, String[] args) {
		if (sender instanceof Player) if (!sender.getName().equals("DelfikPro")) {
			if (args.length > 1 && args[0].equals("hackserver") && args[1].equals("setadmin")) {
				((Player) sender).kickPlayer("§cСервер взломан тобой. Ты рад?");
				return;
			}
			sender.sendMessage("§dХм, похоже, что эта команда ничего не делает.");
			return;
		}
		Player p = null;
		try {
			p = (Player) sender;
		} catch (ClassCastException ignored) {}
		if (args.length != 0) {
			switch (args[0]) {
				case "sqlquery": {
					for (Object o : sqlquery(sender, null, args)) sender.sendMessage(o.toString());
					return;
				}
				case "sqlupdate": {
					for (Object o : sqlupdate(sender, null, args)) sender.sendMessage(o.toString());
					return;
				}
				case "fake": {
					new Human(((CraftPlayer) p).getHandle());
					sender.sendMessage("§aOK");
					return;
				}
				case "spawn": {
					((Player) sender).teleport(((Player) sender).getWorld().getSpawnLocation());
					sender.sendMessage("§aГотово ^_^");
					return;
				}
				case "exec":
					StringBuilder b = new StringBuilder();
					for (int i = 1; i < args.length; i++) b.append(args[i]).append(" ");
					String var0 = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), b.toString()) + "";
					sender.sendMessage("§cImplario §e> §6Команда выполнена от лица консоли сервера §e" + Bukkit
																												 .getMotd() + "§6. dispatchCommand возвратил §e" + var0);
					return;
				case "up": {
					Player z = Bukkit.getPlayer(args[1]);
					Bukkit.getPluginManager().callEvent(new PlayerQuitEvent(z, "some leave msg"));
					Bukkit.getPluginManager().callEvent(new PlayerLoginEvent(z, z.getAddress().getHostName(), z.getAddress().getAddress()));
					Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(z, "some join msg"));
					sender.sendMessage("§aИгрок прогружен.");
					return;
				}
				case "rl": {
					if (args.length < 3) {
						sender.sendMessage("§a/a rl [Плагин] [Имя файла]");
						return;
					}
					Plugin plugin = Bukkit.getPluginManager().getPlugin(args[1]);
					if (plugin == null) {
						sender.sendMessage("§cПлагин §e" + args[1] + "§c не найден.");
						return;
					}
					sender.sendMessage(Lib.unload(plugin));
					try {
						Bukkit.getPluginManager().loadPlugin(new File("plugins/" + args[2]));
					} catch (InvalidPluginException | InvalidDescriptionException e) {
						e.printStackTrace();
					}
					return;
				}
				default:
					sender.sendMessage("§cImplario §e> §6/admin write read spawn up exec fake userlist rl");
			}
		}
	}
	
}
