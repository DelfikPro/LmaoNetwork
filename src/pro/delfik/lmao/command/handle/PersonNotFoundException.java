package pro.delfik.lmao.command.handle;

import org.bukkit.command.CommandSender;

public class PersonNotFoundException extends CustomException {
	public final String person;
	
	public PersonNotFoundException(String person) {
		super(person);
		this.person = person;
	}
	
	@Override
	public void execute(CommandSender sender, LmaoCommand command) {
		sender.sendMessage("§cИгрок §f" + person + "§c не найден.");
	}
}
