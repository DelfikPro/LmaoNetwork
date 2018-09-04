package pro.delfik.lmao.command;

import implario.util.Converter;
import implario.util.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;

public class CommandMultipleCmds extends LmaoCommand {

	public CommandMultipleCmds() {
		super("multiplecmds", Rank.PLAYER, "Много команд");
	}

	@Override
	protected void run(CommandSender sender, String command, String[] args) {
		String string = Converter.mergeArray(args, 0, " ");
		String[] commands = string.split("&&&");
		for (String cmd : commands) Bukkit.dispatchCommand(sender, cmd);
	}
}
