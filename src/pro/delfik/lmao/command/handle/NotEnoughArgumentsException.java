package pro.delfik.lmao.command.handle;

import org.bukkit.command.CommandSender;

public class NotEnoughArgumentsException extends CustomException {
	
	public final String usage;
	
	public NotEnoughArgumentsException(String usage) {
		super(usage);
		this.usage = usage;
	}
	
	@Override
	public void execute(CommandSender sender, LmaoCommand command) {
		sender.sendMessage("§cНедостаточно аргументов");
		sender.sendMessage("§cИспользование: §c/" + command.getName() + " §f" + usage);
	}
}
