package pro.delfik.lmao.command;

import implario.util.Converter;
import implario.util.Rank;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.CustomException;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.command.handle.NotEnoughArgumentsException;
import pro.delfik.lmao.Lmao;
import pro.delfik.lmao.permissions.Perms;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.U;

import java.util.List;

@Cmd(name = "gamemode", rank = Rank.SPONSOR, description = "Изменение игрового режима")
public class CmdGm extends LmaoCommand {
	public static final List<String> SHORT_COMMANDS = Converter.asList("gms", "gmc", "gm0", "gm1", "gm3", "gmw");
	
	@Override
	public void run(Person person, String cmd, String args[]) {
		final GameMode gm;
		final String gmd;
		boolean shortcut = SHORT_COMMANDS.contains(cmd.toLowerCase());
		if (shortcut) {
			gm = U.getGamemode(cmd.charAt(2) + "");
			gmd = U.getGamemodeRepresentation(gm);
		} else {
			requireArgs(args, 1, "[Режим]");
			gm = U.getGamemode(args[0]);
			if (gm == null)
				throw new CustomException(Lmao.p() + "Такого игрового режима не существует. " +
						"Допустимые режимы: §esurvival§c, §ecreative§c, §espectator§c, §eadventure§c.");
			gmd = U.getGamemodeRepresentation(gm);
		}
		if (args.length == (shortcut ? 0 : 1)) {
			Player p = person.getHandle();
			p.setGameMode(gm);
			p.sendMessage(Lmao.p() + "§aВаш игровой режим успешно изменён на §e" + gmd);
		} else {
			if (!Perms.isEnough(person, Rank.KURATOR, true))
				throw new CustomException("LMAO §e> §cИзменять игровой режим другим игрокам можно с §6Куратора");
			String senderD = Lmao.getColoredName(person.getHandle());
			U.selector(person.getHandle(), args[shortcut ? 0 : 1], (p) -> {
				p.setGameMode(gm);
				U.msg(person.getHandle(), Lmao.p() + "§aИгровой режим игрока §e", p, "§a изменён на §e" + gmd);
				U.msg(p.getHandle(), Lmao.p() + "§eВаш игровой режим был изменён на §6" + gmd + "§e игроком " + senderD);
			});
		}
	}
}
