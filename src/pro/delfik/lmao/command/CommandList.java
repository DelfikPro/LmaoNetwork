package pro.delfik.lmao.command;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.core.Person;

import java.util.Collection;

public class CommandList extends ImplarioCommand {
    @Command(name = "list", description = "Список игроков на сервере", usage = "list")
    public void list(CommandSender sender, String cmd, String[] args) {
        StringBuilder sb = new StringBuilder();
        Collection<Person> list = Person.online();
        for (Person p : list) {
            sb.append(p.getDisplayName()).append("§f, ");
        }
        sender.sendMessage("§aСписок игроков на сервере (§e" + list.size() + " всего§a):");
        sender.sendMessage(sb.toString());
    }
}
