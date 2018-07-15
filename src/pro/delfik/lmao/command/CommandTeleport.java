package pro.delfik.lmao.command;

import pro.delfik.lmao.command.handle.NotEnoughPermissionsException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.core.Person;
import pro.delfik.util.Rank;

public class CommandTeleport extends ImplarioCommand {
	@Command(name = "tp", rankRequired = Rank.BUILDER, description = "Телепортация к игроку", usage = "tp [Игрок]", argsRequired = 1)
	public void onCommand(CommandSender sender, String s, String[] args) throws NotEnoughPermissionsException {
		Person p;
		Person dest;
		String destname;
		if (!sender.isOp()) requireRank(sender, Rank.RECRUIT);
		if (args.length == 1) {
			p = Person.get(sender);
			destname = args[0];
		} else if (args.length == 3) {
			p = Person.get(sender);
			World w = p.getLocation().getWorld();
			Location loc;
			try {
				loc = new Location(w, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			} catch (NumberFormatException e) {
				sender.sendMessage(Lmao.p() + "Телепортация на координаты: §e/tp [x] [y] [z]");
				return;
			}
			p.teleport(loc);
			sender.sendMessage(Lmao.p() + "§aВы были телепортированы в точку §e" + loc.getBlockX() + "§a, §e" + loc.getBlockY() + "§a, §e" + loc.getBlockZ());
			return;
		} else {
			requireRank(sender, Rank.KURATOR);
			p = Person.get(args[0]);
			destname = args[1];
		}
		dest = Person.get(destname);
		if (p == null) {
			sender.sendMessage(Lmao.p() + "Игрок §e" + args[0] + "§c не найден.");
			return;
		}
		if (dest == null) {
			sender.sendMessage(Lmao.p() + "Игрок §e" + destname + "§c не найден.");
			return;
		}
		p.teleport(dest.getLocation());
		p.sendMessage(Lmao.p() + "§aВы были телепортированы к игроку §e" + dest.getName());
	}
}
