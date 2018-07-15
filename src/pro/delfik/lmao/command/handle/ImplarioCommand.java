package pro.delfik.lmao.command.handle;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.permissions.Perms;
import pro.delfik.util.Rank;

public abstract class ImplarioCommand {
	
	public static void requireArgs(String[] args, int required, String usage) throws NotEnoughArgumentsException {
		if (args.length < required) throw new NotEnoughArgumentsException(usage);
	}
	
	public void requireRank(CommandSender sender, Rank rank) throws NotEnoughPermissionsException {
		if (!Perms.isEnough(sender, rank)) throw new NotEnoughPermissionsException(rank);
	}
	
	public Person getPerson(String arg) throws PersonNotFoundException {
		Person p = Person.get(arg);
		if (p == null) throw new PersonNotFoundException(arg);
		else return p;
	}
}
