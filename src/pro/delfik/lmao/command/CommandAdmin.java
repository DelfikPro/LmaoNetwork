package pro.delfik.lmao.command;

import implario.util.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.user.Human;

public class CommandAdmin extends LmaoCommand {
	public static String[] quotes = new String[] {
			"Почему бы тебе не выпить чаю?",
			"Ум и интеллигентность. Красота и любовь. Ой, опять про гугла заговорил",
			"Гугал лудшый гугал клас кто ни верет таму в глас"
	};
	
	
	public CommandAdmin() {
		super("admin", Rank.PLAYER, "Взламать сервир нафег!!1");
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
				case "hitdelay": {
					int i = requireInt(args[2]);
					Person v = requirePerson(args[1]);
					v.getHandle().setMaximumNoDamageTicks(i);
					v.getHandle().setNoDamageTicks(i);
					sender.sendMessage("§aDone.");
					return;
				}
				default:
					sender.sendMessage("§cImplario §e> §6/admin sqlquery sqlupdate fake spawn exec up hitdelay");
			}
		}
	}

}
