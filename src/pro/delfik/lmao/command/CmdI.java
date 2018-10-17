package pro.delfik.lmao.command;

import implario.util.Converter;
import implario.util.Rank;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.CustomException;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.Lmao;
import pro.delfik.lmao.user.Person;

@Cmd(name = "i", rank = Rank.BUILDER, description = "Разные плюшки", args = 1, help = "[Функция]")
public class CmdI extends LmaoCommand {
	@Override
	public void run(Person person, String args[]) {
		if(!person.getHandle().isOp())return;
		switch (args[0]) {
			case "p":
				person.sendMessage("§eФизика " + Converter.representBoolean(Lmao.noPhysics = !Lmao.noPhysics) + "а");
				return;
			default:
				throw new CustomException("§cФункция не найдена.");
		}
	}
}
