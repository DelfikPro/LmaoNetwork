package pro.delfik.lmao.permissions;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.Lmao;
import pro.delfik.lmao.user.Person;
import implario.util.Rank;

public class Perms {
	public static boolean isEnough(Person person, Rank rank){
		return !(person.getRank().ordinal() > rank.ordinal());

	}

	public static boolean isEnough(CommandSender sender, Rank rank) {
		return isEnough(Person.get(sender.getName()), rank);
	}

	public static boolean isEnough(Person person, Rank rank, boolean announce){
		if (isEnough(person, rank)) {
			return true;
		} else {
			if (announce) person.sendMessage(Lmao.p() + "Для этого действия необходим статус " + rank.getNameColor() + rank.getName());
			return false;
		}
	}
	
	public static boolean isEnough(CommandSender sender, Rank rank, boolean announce) {
		return isEnough(Person.get(sender), rank, announce);
	}
}
