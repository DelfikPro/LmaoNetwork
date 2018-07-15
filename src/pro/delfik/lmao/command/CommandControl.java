package pro.delfik.lmao.command;

import pro.delfik.lmao.core.connection.database.ServerIO;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.util.Rank;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommandControl extends ImplarioCommand implements TabCompleter {
	
	private static final String prefix = System.getProperty("user.dir") + "/plugins/";
	
	@Command(name = "control", rankRequired = Rank.DEV, argsRequired = 2, usage = "control [update|delete|remove] [Файл]", description = "Управление плагинами на сервере")
	public void control(CommandSender sender, String command, String[] args) {
		String key = args[0];
		String name = args[1];
		switch (key) {
			case "update":
				if (name.equals("all")) {
					File files[] = new File(System.getProperty("user.dir") + "/plugins/").listFiles();
					for (File file : files) {
						if (file.isDirectory()) continue;
						String s1 = file.getName();
						a(sender, s1);
					}
					sender.sendMessage("Обновления были загружены");
					Bukkit.broadcastMessage("§d§lНа сервер было загружено обновление, и был произведён перезапуск.");
					Bukkit.reload();
				} else {
					File file = new File(prefix + name);
					if (file.exists() && a(sender, name)) {
						Bukkit.broadcastMessage("§d§lНа сервер было загружено обновление, и был произведён перезапуск.");
						Bukkit.reload();
					} else sender.sendMessage("Такого плагина нету на данном сервере");
				}
				break;
			case "delete":
				File file = new File(prefix + name);
				if (file.delete()) {
					sender.sendMessage("Плагин был успешно удалён");
					Bukkit.reload();
				} else sender.sendMessage("Плагин не найден");
				break;
			case "load":
				if (a(sender, name)) Bukkit.reload();
				break;
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command c, String command, String[] args) {
		if (args.length <= 1) {
			LinkedList<String> list = new LinkedList<>();
			list.add("load");
			list.add("update");
			list.add("delete");
			return list;
		} else {
			File f[] = new File(System.getProperty("user.dir") + "/plugins").listFiles();
			LinkedList<String> s = new LinkedList<>();
			for (File aF : f) {
				if (!aF.isDirectory())
					s.add(aF.getName());
			}
			return getForContains(s.toArray(new String[]{}), args[1]);
		}
	}
	
	public static List<String> getForContains(String search[], String prefix){
		prefix = prefix.toLowerCase();
		ArrayList<String> l = new ArrayList<>();
		for(String s : search)
			if(s.toLowerCase().startsWith(prefix))l.add(s);
		return l;
	}
	
	static List<String> getForContains(String search[]){
		return getForContains(search, "");
	}
	
	private boolean a(CommandSender sender, String plugin) {
		String file = ServerIO.connect("getfile updater/" + plugin);
		if (file == null || file.length() == 0) {
			sender.sendMessage(plugin + " не был найден в базе обновлений");
			return false;
		}
		OutputStream writer = null;
		File file1 = new File(System.getProperty("user.dir") + "/plugins/" + plugin);
		try {
			file1.createNewFile();
			writer = new FileOutputStream(file1);
			char bytes[] = file.toCharArray();
			for (char c : bytes) writer.write(c);
			writer.flush();
			writer.close();
			sender.sendMessage(plugin + " был загружен");
		} catch (IOException ex) {
			try {
				writer.close();
			} catch (Exception ignored) {
			}
			sender.sendMessage(ex.getMessage());
			return false;
		}
		return true;
	}
}
