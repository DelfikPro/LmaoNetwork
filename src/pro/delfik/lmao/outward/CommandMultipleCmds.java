package pro.delfik.lmao.outward;

import implario.util.Converter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMultipleCmds implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		String string = Converter.mergeArray(strings, 0, " ");
		String[] commands = string.split("&&&");
		for (String cmd : commands) Bukkit.dispatchCommand(commandSender, cmd);
		return true;
	}
}
