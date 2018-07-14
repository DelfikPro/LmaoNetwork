package pro.delfik.lmao.command;

import lib.Generate;
import lib.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.core.Person;

public class CommandPlayer extends ImplarioCommand {
	
	private static final ItemStack STATS = Generate.itemstack(Material.ITEM_FRAME, 1, 0, "§f>> §6§lСтатистика §f<<", "§e§oПодробная статистика по всем режимам");
	private static final ItemStack INFO = Generate.itemstack(Material.BOOK, 1, 0, "§f>> §6§lПаспорт игрока §f<<", "§e§oПодробная информация об игроке");
	private static final ItemStack ACTIONS = Generate.charge(Color.YELLOW,"§f>> §6§lДействия §f<<", "§e§oПосмотреть доступные действия с игроком");
	
	
	@Command(name = "player", argsRequired = 1, usage = "player [Игрок]", description = "Информация об игроке")
	public void commandPlayer(CommandSender sender, String command, String[] args) {
		Person v = Person.get(args[0]);
		if (v == null) {
			sender.sendMessage("§6Скоро можно будет проверять и оффлайн-игроков.");
		} else {
			GUI gui = new GUI(Bukkit.createInventory(null,27,"§0§lИгрок " + args[0]));
			gui.put(10, STATS, p -> p.closeInventory());
			gui.put(11, INFO, p -> p.closeInventory());
			gui.put(12, ACTIONS, p -> p.chat("/actions " + args[0]));
			((Player) sender).openInventory(gui.getInventory());
		}
	}
}
