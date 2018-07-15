package pro.delfik.lmao.command;

import lib.gui.GUILoading;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.command.handle.PersonNotFoundException;
import pro.delfik.lmao.core.Person;
import pro.delfik.util.Rank;

public class CommandBanList extends ImplarioCommand {
	@Command(name = "banlist", rankRequired = Rank.MODER)
	public void banlist(CommandSender sender, String command, String[] args) throws PersonNotFoundException {
		Person p = getPerson(sender.getName());
		p.getHandle().openInventory(GUILoading.i());
	}
}
