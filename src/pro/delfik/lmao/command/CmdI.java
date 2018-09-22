package pro.delfik.lmao.command;

import implario.util.Converter;
import implario.util.Rank;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.CustomException;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.core.Lmao;

public class CmdI extends LmaoCommand {

	public CmdI() {
		super("i", Rank.BUILDER, "Разные плюшки");
	}

	@Override
	protected void run(CommandSender sender, String command, String[] args) {
		if(!sender.isOp())return;
		requireArgs(args, 1, "[Функция]");
		switch (args[0]) {
			case "p":
				sender.sendMessage("§eФизика " + Converter.representBoolean(Lmao.noPhysics = !Lmao.noPhysics) + "а");
				return;
			default:
				throw new CustomException("§cФункция не найдена.");
		}
	}
}
