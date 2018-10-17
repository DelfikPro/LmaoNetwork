package pro.delfik.lmao.command;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.Lmao;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.Registrar;
import pro.delfik.lmao.util.U;
import implario.util.Rank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Cmd(name = "help", description = "Помощь по командам сервера")
public class CmdHelp extends LmaoCommand {
	public static List<Set<String>> playerpages = null;
	public static void initPages() {
		playerpages = new ArrayList<>();
		int field = 0;
		Set<String> set = new HashSet<>();
		for (Registrar r : Registrar.getRegistrars().values()) {
			for (LmaoCommand cmd : r.getCommands()) {

				Rank rank = cmd.getRequiredRank();
				if (rank != Rank.PLAYER) continue;
				String text = "§7/" + cmd.getName() + " §e- " + cmd.getDescription();
				set.add(text);
				field++;
				if (field > 8) {
					field = 0;
					playerpages.add(set);
					set = new HashSet<>();
				}
			}
		}
		playerpages.add(set);
	}

	@Override
	public void run(Person person, String args[]) {
		int page = args.length == 0 ? 1 : requireInt(args[0]);
		if (playerpages == null) initPages();
		Set<String> ts;
		try {
			ts = playerpages.get(page - 1);
		} catch (Exception e) {
			person.sendMessage(Lmao.p() + "§cСтраницы под номером §e" + page + "§c не существует.");
			return;
		}
		person.sendMessage("§aПомощь по командам сервера, страница §e" + page + "§a:");
		for (String tc : ts)
			person.sendMessage(tc);
	}

}
