package pro.delfik.lmao.command.handle;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.permissions.Perms;
import pro.delfik.lmao.permissions.Rank;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImplarioExecutor implements CommandExecutor {
	private final Method method;
	private final Object invoke;
	private final String name;
	private final Rank needed;
	private final int argsNeeds;
	private final String using;
	
	
	public ImplarioExecutor(Command cmd, Method method, Object invoke) {
		this.method = method;
		this.invoke = invoke;
		this.name = cmd.name();
		this.needed = cmd.rankRequired();
		this.argsNeeds = cmd.argsRequired();
		this.using = (this.name + " " + cmd.usage());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String command, String[] args) {
		try {
			if (!Perms.isEnough(sender, needed, true)) return false;
			if (args.length < this.argsNeeds) {
				sender.sendMessage(Lmao.p() + "Использование: §e/" + this.using);
				return false;
			} else {
				this.method.invoke(this.invoke, sender, command, args);
				return true;
			}
		} catch (Exception ex) {
			Throwable cause = ex;
			while (cause.getCause() != null) cause = cause.getCause();
			if (cause instanceof NotEnoughArgumentsException) {
				sender.sendMessage(Lmao.p() + "Недостаточно аргументов.");
				sender.sendMessage(Lmao.p() + "Использование: §e/" + command + " " + ((NotEnoughArgumentsException) cause).usage);
				return false;
			}
			if (cause instanceof NotEnoughPermissionsException) {
				sender.sendMessage(Lmao.p() + "Для этого действия необходим статус §e" + ((NotEnoughPermissionsException) cause).required);
				return false;
			}
			if (cause instanceof ClassCastException) {
				sender.sendMessage("§cThe command §e/" + command + "§c requires being executed by a player.");
				return false;
			}
			if (cause instanceof PersonNotFoundException) {
				sender.sendMessage(Lmao.p() + "Игрок с ником §e" + ((PersonNotFoundException) cause).person + "§c не найден.");
				return false;
			}
			ex.printStackTrace();
			sender.sendMessage("§cПри выполнении команды произошла непредвиденная ошибка.");
			sender.sendMessage("§cПожалуйста, отправьте скрин администрации.");
			sender.sendMessage("§cCommand: §e/" + command + " " + String.join(" ", args));
			sender.sendMessage("§cException: §e" + ex.getClass().getName());
			sender.sendMessage("§cTimestamp: §e" + new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy").format(new Date()));
			sender.sendMessage("§cLine: §e" + cause.getStackTrace()[0].getClassName() + " - line " + cause.getStackTrace()[0].getLineNumber());
			return false;
		}
	}
}
