package pro.delfik.lmao.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.command.handle.ImplarioExecutor;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.util.Rank;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registrar {
	private static final PluginManager m = Bukkit.getPluginManager();
	private static final HashMap<String, String> usage = new HashMap<>();
	private static final HashMap<String, String> description = new HashMap<>();
	private static final HashMap<String, Registrar> list = new HashMap<>();
	public static final HashMap<String, Rank> ranks = new HashMap<>();
	private final Plugin p;
	private final List<String> commands = new ArrayList<>();

	public Registrar(Plugin plugin) {
		this.p = plugin;
		list.put(plugin.getName(), this);
	}

	public static Registrar get(String plugin) {return list.get(plugin);}
	public static Registrar get(Plugin plugin) {return list.get(plugin.getName());}
	public static String getUsage(String command) {return usage.get(command);}
	public static String getDecription(String command) {return description.get(command);}

	public Plugin getPlugin() {
		return p;
	}

	public static HashMap<String, Registrar> getRegistrars(){
		return list;
	}

	public List<String> getCommands(){
		return commands;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Registrar{Commands:[");
		for (int i = 0; i < commands.size(); i++) {
			sb.append(commands.get(i));
			if (i + 1 != commands.size()) sb.append(",");
		}
		sb.append("],Plugin:\"").append(p.getName()).append("\"}");
		return sb.toString();
	}

	public void regCommand(LmaoCommand command) {
	
	}
	
	public void regCommand(ImplarioCommand command) {
		Method[] ms = command.getClass().getMethods();
		for (Method m : ms) {
			Command cmd1 = m.getAnnotation(Command.class);
			if (cmd1 == null) continue;
			String cmd = cmd1.name();
			PluginCommand bukkitCmd = Bukkit.getPluginCommand(cmd);
			bukkitCmd.setExecutor(regCommand(cmd1, m, command));
			Registrar.usage.put(cmd, cmd1.usage());
			Registrar.description.put(cmd, cmd1.description());
			Registrar.ranks.put(cmd, cmd1.rankRequired());
			commands.add(cmd);
			if (command instanceof TabCompleter) bukkitCmd.setTabCompleter((TabCompleter) command);
		}
	}

	private ImplarioExecutor regCommand(Command cmd, Method onCommand, ImplarioCommand command) {
		return new ImplarioExecutor(cmd, onCommand, command);
	}

	public void regCommand(String cmd, CommandExecutor executor, Rank rank, String description, String usage, TabCompleter tab) {
		PluginCommand c = Bukkit.getPluginCommand(cmd);
		if (executor != null) c.setExecutor(executor);
		if (tab != null) c.setTabCompleter(tab);
		Registrar.usage.put(cmd, usage);
		Registrar.description.put(cmd, description);
		Registrar.ranks.put(cmd, rank);
		commands.add(cmd);
	}

	public void regCommand(String cmd, Rank rank, String description, String usage) {
		Registrar.usage.put(cmd, usage);
		Registrar.description.put(cmd, description);
		Registrar.ranks.put(cmd, rank);
		commands.add(cmd);
	}

	public void regEvent(Listener l) {
		m.registerEvents(l, p);
	}
}
