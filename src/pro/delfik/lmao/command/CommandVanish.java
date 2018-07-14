package pro.delfik.lmao.command;

import pro.delfik.lmao.permissions.Rank;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.core.Person;

public class CommandVanish extends ImplarioCommand {
	@Command(name = "vanish", rankRequired = Rank.RECRUIT, usage = "vanish", description = "Управление ванишем")
	public boolean onCommand(CommandSender sender, String s, String[] strings) {
		Person p = Person.get(sender);
		sender.sendMessage("§aРежим зрителя установлен на §e" + p.vanish());
		return true;
	}
}
