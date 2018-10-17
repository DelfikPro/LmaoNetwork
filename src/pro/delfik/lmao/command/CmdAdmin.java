package pro.delfik.lmao.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.user.Human;

@Cmd(name = "admin", description = "Взламать сервир нафег!!1")
public class CmdAdmin extends LmaoCommand {
	@Override
	public void run(Person person, String args[]) {
		if (!person.getName().equals("DelfikPro")) {
			if (args.length > 1 && args[0].equals("hackserver") && args[1].equals("setadmin")) {
				person.getHandle().kickPlayer("§cСервер взломан тобой. Ты рад?");
				return;
			}
			person.sendMessage("§dХм, похоже, что эта команда ничего не делает.");
			return;
		}
		Player p = person.getHandle();
		if (args.length != 0) {
			switch (args[0]) {
				case "fake": {
					new Human(((CraftPlayer) p).getHandle());
					person.sendMessage("§aOK");
					return;
				}
				case "spawn": {
					p.teleport(p.getWorld().getSpawnLocation());
					person.sendMessage("§aГотово ^_^");
					return;
				}
				case "exec":
					StringBuilder b = new StringBuilder();
					for (int i = 1; i < args.length; i++) b.append(args[i]).append(" ");
					String var0 = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), b.toString()) + "";
					person.sendMessage("§cImplario §e> §6Команда выполнена от лица консоли сервера §e" + Bukkit
							.getMotd() + "§6. dispatchCommand возвратил §e" + var0);
					return;
				case "up": {
					Player z = Bukkit.getPlayer(args[1]);
					Bukkit.getPluginManager().callEvent(new PlayerQuitEvent(z, "some leave msg"));
					Bukkit.getPluginManager().callEvent(new PlayerLoginEvent(z, z.getAddress().getHostName(), z.getAddress().getAddress()));
					Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(z, "some join msg"));
					person.sendMessage("§aИгрок прогружен.");
					return;
				}
				case "hitdelay": {
					int i = requireInt(args[2]);
					Person v = requirePerson(args[1]);
					v.getHandle().setMaximumNoDamageTicks(i);
					v.getHandle().setNoDamageTicks(i);
					person.sendMessage("§aDone.");
					return;
				}
				default:
					person.sendMessage("§cImplario §e> §6/admin sqlquery sqlupdate fake spawn exec up hitdelay");
			}
		}
	}

}
