package pro.delfik.lmao.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pro.delfik.lmao.command.handle.LmaoCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registrar {
	private static final PluginManager m = Bukkit.getPluginManager();
	private static final HashMap<String, Registrar> list = new HashMap<>();
	private final Plugin p;
	private final List<LmaoCommand> commands = new ArrayList<>();

	public Registrar(Plugin plugin) {
		this.p = plugin;
		list.put(plugin.getName(), this);
	}

	public static Registrar get(String plugin) {return list.get(plugin);}
	public static Registrar get(Plugin plugin) {return list.get(plugin.getName());}

	public Plugin getPlugin() {
		return p;
	}

	public static HashMap<String, Registrar> getRegistrars(){
		return list;
	}

	public List<LmaoCommand> getCommands(){
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
		PluginCommand bukkitCommand = Bukkit.getPluginCommand(command.getName());
		bukkitCommand.setExecutor(command);
		commands.add(command);
		try {
			if (command.getClass().getMethod("tabComplete", CommandSender.class, String.class, int.class).getDeclaringClass().equals(LmaoCommand.class))
				bukkitCommand.setTabCompleter(command);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public void regEvent(Listener l) {
		m.registerEvents(l, p);
	}
}
