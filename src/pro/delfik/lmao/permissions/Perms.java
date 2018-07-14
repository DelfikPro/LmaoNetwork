package pro.delfik.lmao.permissions;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.core.User;

public class Perms {
	
	public static boolean isEnough(CommandSender sender, Rank rank) {
		Rank player = User.getUser(sender.getName()).getRank();
		return !(player.ordinal() > rank.ordinal());
	}
	
	public static boolean isEnough(CommandSender sender, Rank rank, boolean announce) {
		if (isEnough(sender, rank)) {
			return true;
		} else {
			if (announce) sender.sendMessage(Lmao.p() + "Для этого действия необходим статус " + rank.getNameColor() + rank.getName());
			return false;
		}
	}
	
}
