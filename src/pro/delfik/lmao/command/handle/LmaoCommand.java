package pro.delfik.lmao.command.handle;

import implario.util.Converter;
import implario.util.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.U;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class LmaoCommand implements CommandExecutor, TabCompleter {
	private final boolean cmd;
	private final String description;
	private final String help;
	private Rank required;
	private final String name;
	private final int args;

	protected LmaoCommand(){
		Cmd cmd = getClass().getAnnotation(Cmd.class);
		this.cmd = true;
		this.help = cmd.help();
		this.required = cmd.rank();
		this.name = cmd.name();
		this.args = cmd.args();
		this.description = cmd.description();
	}
	
	protected LmaoCommand(String name, Rank required, String description) {
		cmd = false;
		this.name = name;
		this.description = description;
		this.required = required;
		this.args = 0;
		this.help = "";
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command bukkkitcommand, String command, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			run(sender, command, args);
			return true;
		}
		Person user = Person.get(sender);
		try {
			requireRank(user, getRequiredRank());
			if(cmd) requireArgs(args, this.args, help);
			run(user, command, args);
		} catch (ClassCastException ex) {
			U.msg(sender, "§cGo away evil console :c");
		} catch (Throwable t) {
			Throwable cause = t;
			while (cause.getCause() != null) cause = cause.getCause();
			if (cause instanceof CustomException) {
				((CustomException) cause).execute(sender, this);
				return false;
			}
			if (cause instanceof NumberFormatException) {
				U.msg(sender, "§с'§f" + cause.getMessage() + "§c' не является допустимым числом.");
				return false;
			}
			t.printStackTrace();
			U.msg(sender, "§cПри выполнении команды произошла неизвестная ошибка.");
			U.msg(sender, "§cПожалуйтса, отправьте скрин сообщения администрации:");
			U.msg(sender, "§cCommand: §e/" + command + " " + String.join(" ", args));
			U.msg(sender,"§cException: §e" + cause.getClass().getName());
			U.msg(sender,"§cTimestamp: §e" + new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy").format(new Date()));
			U.msg(sender,"§cLine: §e" + cause.getStackTrace()[0].getClassName() + " - line " + cause.getStackTrace()[0].getLineNumber());
		}
		return true;
	}
	
	protected void run(CommandSender sender, String command, String[] args){}

	public void run(Person person, String command, String args[]){
		if(cmd) run(person, args);
		else run(person.getHandle(), command, args);
	}

	public void run(Person person, String args[]){}

	public Rank getRequiredRank() {
		return required == null ? Rank.PLAYER : required;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static void msg(CommandSender sender, Object... o) {
		U.msg(sender, o);
	}
	
	public static void requireArgs(String[] args, int required, String usage) {
		if (args.length < required) throw new NotEnoughArgumentsException(usage);
	}

	public static void requireRank(Person person, Rank rank){
		if (!person.hasRank(rank)) throw new NotEnoughPermissionsException(rank);
	}
	
	public static void requireRank(CommandSender sender, Rank rank) {
		requireRank(Person.get(sender), rank);
	}
	
	public static Person requirePerson(String arg) {
		Person u = Person.get(arg);
		if (u == null) throw new PersonNotFoundException(arg); else return u;
	}
	
	public static Player requirePlayer(String arg) {
		Player p = Bukkit.getPlayer(arg);
		if (p == null) throw new PersonNotFoundException(arg); else return p;
	}
	
	public static int requireInt(String arg) {
		try {
			return Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(arg);
		}
	}
	
	
	public List<String> tabComplete(CommandSender sender, String arg, int number) {
		return Converter.tabCompleteList(Person.online(), Person::getName, arg);
	}
	
	@Override
	public final List<String> onTabComplete(CommandSender commandSender, Command bukkitCommand, String command, String[] args) {
		int argnumber = args.length - 1;
		if (argnumber < 0) return new ArrayList<>();
		String currentArg = args[argnumber];
		return tabComplete(commandSender, currentArg, argnumber);
	}
	
	public String getName() {
		return name;
	}
}
