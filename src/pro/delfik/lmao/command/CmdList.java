package pro.delfik.lmao.command;

import implario.util.Converter;
import implario.util.Rank;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;

@Cmd(name = "list", description = "Показать список игроков на сервере")
public class CmdList extends LmaoCommand {
    @Override
    public void run(Person person, String args[]) {
        person.sendMessage("§aСписок игроков на сервере (§e" + Person.online().size() + " всего§a):");
        person.sendMessage(Converter.merge(Person.online(), Person::getDisplayName, "§7, §f"));
    }
}
