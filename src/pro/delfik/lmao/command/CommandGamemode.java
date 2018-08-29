package pro.delfik.lmao.command;

import lib.Converter;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.command.handle.NotEnoughArgumentsException;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.permissions.Perms;
import pro.delfik.lmao.util.U;
import implario.util.Rank;

import java.util.List;

public class CommandGamemode extends LmaoCommand {
	
	public CommandGamemode() {
		super("gamemode", Rank.SPONSOR, "Изменение игрового режима");
	}
	
	public static final List<String> SHORT_COMMANDS = Converter.toList("gms", "gmc", "gm0", "gm1", "gm3", "gmw");
	
	@Override
	public void run(CommandSender sender, String cmd, String[] args) throws NotEnoughArgumentsException {
		final GameMode gm;
		final String gmd;
		boolean shortcut = SHORT_COMMANDS.contains(cmd.toLowerCase());
		if (shortcut) {
			gm = Converter.getGamemode(cmd.charAt(2) + "");
			gmd = Converter.getGamemodeRepresentation(gm);
		} else {
			requireArgs(args, 1, "[Режим]");
			gm = Converter.getGamemode(args[0]);
			if (gm == null) {
				sender.sendMessage(Lmao.p() + "Такого игрового режима не существует. Допустимые режимы: §esurvival§c, " +
										   "§ecreative§c, §espectator§c, §eadventure§c.");
				return;
			}
			gmd = Converter.getGamemodeRepresentation(gm);
		}
		if (args.length == (shortcut ? 0 : 1)) {
			Player p = (Player) sender;
			p.setGameMode(gm);
			p.sendMessage(Lmao.p() + "§aВаш игровой режим успешно изменён на §e" + gmd);
		} else {
			if (!Perms.isEnough(sender, Rank.KURATOR, true)) {
				sender.sendMessage("LMAO §e> §cИзменять игровой режим другим игрокам можно с §6Куратора");
				return;
			}
			String senderD = Lmao.getColoredName(sender);
			U.selector(sender, args[shortcut ? 0 : 1], (p) -> {
				p.setGameMode(gm);
				U.msg(sender, Lmao.p() + "§aИгровой режим игрока §e", p, "§a изменён на §e" + gmd);
				U.msg(p.getHandle(), Lmao.p() + "§eВаш игровой режим был изменён на §6" + gmd + "§e игроком " + senderD);
			});
		}
	}
}
