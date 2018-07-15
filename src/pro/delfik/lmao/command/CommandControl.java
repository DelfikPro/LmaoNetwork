package pro.delfik.lmao.command;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.util.Converter;
import pro.delfik.util.Rank;

import java.io.File;
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
		//TODO
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
}
