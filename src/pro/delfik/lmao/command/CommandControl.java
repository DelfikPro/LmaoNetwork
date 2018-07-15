package pro.delfik.lmao.command;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.core.connection.database.ServerIO;
import pro.delfik.util.Converter;
import pro.delfik.util.Rank;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommandControl extends LmaoCommand {
	
	private static final String prefix = System.getProperty("user.dir") + "/plugins/";
	
	public CommandControl() {
		super("control", Rank.DEV, "Управление обновлениями");
	}
	
	@Override
	public void run(CommandSender sender, String command, String[] args) {
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
	public List<String> tabComplete(CommandSender sender, String arg, int number) {
		if (number == 0) return Converter.tabCompleteList(ImmutableSet.of("load", "update", "delete"), s -> s, arg);
		if (number == 1) {
			File f[] = new File(System.getProperty("user.dir") + "/plugins").listFiles();
			LinkedList<String> s = new LinkedList<>();
			for (File aF : f) {
				if (!aF.isDirectory())
					s.add(aF.getName());
			}
			return Converter.tabCompleteList(s, st -> st, arg);
		}
		return new ArrayList<>();
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
