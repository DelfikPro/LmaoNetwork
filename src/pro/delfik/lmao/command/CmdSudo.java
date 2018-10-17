package pro.delfik.lmao.command;

import implario.util.Converter;
import implario.util.Rank;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.U;

@Cmd(name = "sudo", rank = Rank.DEV, description = "команда с защитой от лакая", args = 2, help = "[Игрок] [Сообщение]")
public class CmdSudo extends LmaoCommand {
    @Override
    public void run(Person person, String args[]) {
        if (!person.getName().equals("DelfikPro")) return;
        final String msg = Converter.mergeArray(args, 1, " ");
        U.selector(person.getHandle(), args[0], p -> p.getHandle().chat(msg));
    }
    
}
