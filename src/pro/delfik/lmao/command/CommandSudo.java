package pro.delfik.lmao.command;

import lib.Converter;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.util.U;
import pro.delfik.util.Rank;

public class CommandSudo extends LmaoCommand {
    
    public CommandSudo() {
        super("sudo", Rank.DEV, "команда с защитой от лакая");
    }
    
    @Override
    public void run(CommandSender sender, String arg2, String[] args) {
        if (!sender.getName().equals("DelfikPro")) return;
        final String msg = Converter.mergeArray(args, 1, " ");
        U.selector(sender, args[0], p -> p.getHandle().chat(msg));
    }
    
}
