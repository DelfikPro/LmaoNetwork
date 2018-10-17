package pro.delfik.lmao.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import implario.util.Rank;
import pro.delfik.lmao.user.Person;

@Cmd(name = "stop", rank = Rank.KURATOR, description = "Остановка кластера")
public class CmdStop extends LmaoCommand {
	@Override
	public void run(Person person, String args[]) {
		Bukkit.shutdown();
	}
}
