package pro.delfik.lmao.command;

import implario.util.Converter;
import implario.util.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;

@Cmd(name = "multiplecmds", description = "Много команд")
public class CmdMultipleCmds extends LmaoCommand {
	@Override
	public void run(Person person, String args[]) {
		String string = Converter.mergeArray(args, 0, " ");
		String[] commands = string.split("&&&");
		CommandSender sender = person.getHandle();
		for (String cmd : commands) Bukkit.dispatchCommand(sender, cmd);
	}
}
