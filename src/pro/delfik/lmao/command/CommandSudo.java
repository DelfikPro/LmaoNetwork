package pro.delfik.lmao.command;

import lib.Converter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.util.U;
import pro.delfik.util.Rank;

public class CommandSudo extends ImplarioCommand {
    @Command(name = "sudo", rankRequired = Rank.DEV, description = "Выполнение команды от лица игрока", usage = "sudo [Игрок] [Команда]", argsRequired = 2)
    public boolean onCommand(CommandSender sender, String arg2, String[] args) {
        if (!sender.getName().equals("DelfikPro")) {sender.sendMessage("§6Какая ещё команда /sudo? Нет никакой команды /sudo..."); return false;}
        final String msg;
        final boolean chat;
        if (args[1].startsWith("c:")) {
            msg = Converter.mergeArray(args, 1, " ").substring(2);
            chat = true;
        } else {
            msg = Converter.mergeArray(args, 1, " ");
            chat = false;
        }
        U.selector(sender, args[0], (p) -> {
            if (chat) {
                p.getHandle().chat(msg);
                sender.sendMessage(Lmao.p() + "§aСообщение отправлено от лица §e" + p.getDisplayName());
            } else {
                sender.sendMessage(Lmao.p() + "§aКоманда выполнена на §e" + Bukkit.dispatchCommand(p.getHandle(), msg) + "§a от лица §e" + p.getDisplayName());
            }
        });
        return true;
    }
    
}
