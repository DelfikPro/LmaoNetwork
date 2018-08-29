package pro.delfik.lmao.command;

import lib.gui.GUILoading;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.command.handle.PersonNotFoundException;
import pro.delfik.lmao.core.Person;
import implario.util.Rank;

public class CommandBanList extends LmaoCommand {
	
	public CommandBanList() {
		super("banlist", Rank.RECRUIT, "Просмотр списка банов");
	}
	
	@Override
	public void run(CommandSender sender, String command, String[] args) throws PersonNotFoundException {
		Person p = requirePerson(sender.getName());
		p.getHandle().openInventory(GUILoading.i());
	}
}
