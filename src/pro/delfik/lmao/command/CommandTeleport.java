package pro.delfik.lmao.command;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.CustomException;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.command.handle.NotEnoughPermissionsException;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.core.Person;
import pro.delfik.util.Rank;

public class CommandTeleport extends LmaoCommand {
	
	public CommandTeleport() {
		super("tp", Rank.VIP, "");
	}
	
	public void run(CommandSender sender, String s, String[] args) throws NotEnoughPermissionsException {
		requireArgs(args, 1, "[Игрок]");
		Person p;
		Person dest;
		String destname;
		if (!sender.isOp()) {
			requireRank(sender, Rank.RECRUIT);
			p = Person.get(sender);
			if (!p.isVanish()) try {
				requireRank(sender, Rank.KURATOR);
			} catch (NotEnoughPermissionsException ex) {
				throw new CustomException("§cВы можете телепортироваться к игрокам только в ванише (/vanish)");
			}
		}
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
			if (loc.getBlockY() > 10000) throw new CustomException("§c§lФАТАЛЬНАЯ АШЫПКА СЕРВЕР УПАДЁТ СЧАС БЕГИТЕ!!!");
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
