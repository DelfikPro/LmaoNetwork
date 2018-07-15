package pro.delfik.lmao.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.util.Rank;

public class CommandStop extends ImplarioCommand {
	@Command(name = "stop", rankRequired = Rank.ADMIN)
	public void stop(CommandSender sender, String command, String args[]) {
		Bukkit.shutdown();
	}
}
