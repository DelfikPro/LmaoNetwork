package pro.delfik.lmao.command;

import lib.Converter;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.core.Person;
import implario.util.Rank;

public class CommandList extends LmaoCommand {
    
    public CommandList() {
        super("list", Rank.PLAYER, "Показать список игроков на сервере");
    }
    
    @Override
    public void run(CommandSender sender, String cmd, String[] args) {
        sender.sendMessage("§aСписок игроков на сервере (§e" + Person.online().size() + " всего§a):");
        sender.sendMessage(Converter.merge(Person.online(), Person::getDisplayName, "§7, §f"));
    }
}
