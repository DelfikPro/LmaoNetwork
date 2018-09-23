package pro.delfik.lmao.command;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;
import implario.util.Rank;

public class CommandVanish extends LmaoCommand {
	
	public CommandVanish() {
		super("vanish", Rank.RECRUIT, "Переключение режима зрителя");
	}
	
	public void run(CommandSender sender, String s, String[] strings) {
		Person p = Person.get(sender);
		sender.sendMessage("§aРежим зрителя установлен на §e" + p.vanish());
	}
}
