package pro.delfik.lmao.command.handle;

import org.bukkit.command.CommandSender;
import pro.delfik.util.Rank;

public class NotEnoughPermissionsException extends CustomException {
	
	public final Rank required;
	
	public NotEnoughPermissionsException(Rank required) {
		super(required.toString());
		this.required = required;
	}
	
	@Override
	public void execute(CommandSender sender, LmaoCommand command) {
		sender.sendMessage("§cДля этого действия необходим статус §f" + required.represent());
	}
}
