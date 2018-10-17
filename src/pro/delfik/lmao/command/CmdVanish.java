package pro.delfik.lmao.command;

import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;
import implario.util.Rank;

@Cmd(name = "vanish", rank = Rank.RECRUIT, description = "Переключение режима зрителя")
public class CmdVanish extends LmaoCommand {
	@Override
	public void run(Person person, String args[]) {
		person.sendMessage("§aРежим зрителя установлен на §e" + person.vanish());
	}
}
