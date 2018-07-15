package pro.delfik.lmao.command;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.core.Registrar;
import pro.delfik.lmao.util.U;
import pro.delfik.util.Rank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandHelp extends ImplarioCommand {
	
	public static List<Set<TextComponent>> playerpages = null;
	
	@Command(name = "help", usage = "help [Страница|Команда]", description = "Помощь по командам сервера")
	public boolean onCommand(CommandSender sender, String s, String[] args) {
		int page = 1;
		if (args.length != 0) {
			try {page = Integer.parseInt(args[0]);} catch (NumberFormatException e) {
				String usage = Registrar.getUsage(args[0]);
				String description = Registrar.getDecription(args[0]);
				if (usage == null || description == null) {
					sender.sendMessage(Lmao.p() + "§cКоманда §e" + args[0] + "§c не найдена.");
					return false;
				}
				U.msg(sender, U.simple("§7/" + usage + "§e - " + description, "§e> Нажмите для ввода команды <",
						"/" + args[0] + " "));
				return true;
			}
		}
		if (playerpages == null) initPages();
		Set<TextComponent> ts;
		try {ts = playerpages.get(page - 1);} catch (Exception e) {sender.sendMessage(Lmao.p() + "§cСтраницы под номером " +
																							  "§e" + page + "§c не существует."); return false;}
		sender.sendMessage("§aПомощь по командам сервера, страница §e" + page + "§a:");
		for (TextComponent tc : ts) {
			U.msg(sender, tc);
		}
		return true;
	}
	
	public static void initPages() {
		playerpages = new ArrayList<>();
		int field = 0;
		Set<TextComponent> set = new HashSet<>();
		for (Registrar r : Registrar.getRegistrars().values()) {
			for (String cmd : r.getCommands()) {
				Rank rank = Registrar.ranks.get(cmd);
				String text = "§7/" + Registrar.getUsage(cmd) + " §e- " + Registrar.getDecription(cmd) + "§7 (" + rank.getNameColor() + rank.getName() + "§7)";
				set.add(U.simple(text, "§e/" + Registrar.getUsage(cmd) + "\n§7" + Registrar.getDecription(cmd) + "\n§6> " + "Нажмите для ввода команды <", "/" + cmd + " "));
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
