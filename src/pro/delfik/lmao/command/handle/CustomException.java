package pro.delfik.lmao.command.handle;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.util.U;

public class CustomException extends RuntimeException {
	public CustomException(String s) {
		super(s);
	}
	
	public void execute(CommandSender sender, LmaoCommand command){
		U.msg(sender, getMessage());
	}
}
