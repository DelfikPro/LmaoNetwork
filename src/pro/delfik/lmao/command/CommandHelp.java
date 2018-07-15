package pro.delfik.lmao.command;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.core.Registrar;
import pro.delfik.lmao.util.U;
import pro.delfik.util.Rank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandHelp extends LmaoCommand {
	
	public CommandHelp() {
		super("help", Rank.PLAYER, "Помощь по командам сервера");
	}
	
	public static List<Set<String>> playerpages = null;
	
	@Override
	public void run(CommandSender sender, String s, String[] args) {
		int page = 1;
		if (playerpages == null) initPages();
		Set<String> ts;
		try {
			ts = playerpages.get(page - 1);
		} catch (Exception e) {
			sender.sendMessage(Lmao.p() + "§cСтраницы под номером §e" + page + "§c не существует.");
			return;
		}
		sender.sendMessage("§aПомощь по командам сервера, страница §e" + page + "§a:");
		for (String tc : ts) {
			U.msg(sender, tc);
		}
	}
	
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
}
