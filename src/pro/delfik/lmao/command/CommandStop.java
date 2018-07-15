package pro.delfik.lmao.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.util.Rank;

public class CommandStop extends LmaoCommand {
	
	public CommandStop() {
		super("stop", Rank.KURATOR, "Остановка кластера");
	}
	
	public void run(CommandSender sender, String command, String args[]) {
		Bukkit.shutdown();
	}
}
