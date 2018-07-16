package pro.delfik.lmao.command;

import lib.Generate;
import lib.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.core.Person;
import pro.delfik.util.Rank;

public class CommandPlayer extends LmaoCommand {
	
	private static final ItemStack STATS = Generate.itemstack(Material.ITEM_FRAME, 1, 0, "§f>> §6§lСтатистика §f<<", "§e§oПодробная статистика по всем режимам");
	private static final ItemStack INFO = Generate.itemstack(Material.BOOK, 1, 0, "§f>> §6§lПаспорт игрока §f<<", "§e§oПодробная информация об игроке");
	private static final ItemStack ACTIONS = Generate.charge(Color.YELLOW,"§f>> §6§lДействия §f<<", "§e§oПосмотреть доступные действия с игроком");
	
	public CommandPlayer() {
		super("player", Rank.PLAYER, "Меню с информацией об игроке");
	}
	
	
	public void run(CommandSender sender, String command, String[] args) {
		String name = args.length == 0 ? sender.getName() : args[0];
		Person v = Person.get(name);
		if (v == null) {
			sender.sendMessage("§6Скоро можно будет проверять и оффлайн-игроков.");
		} else {
			GUI gui = new GUI(Bukkit.createInventory(null,27,"§0§lИгрок " + name));
			gui.put(10, STATS, Player::closeInventory);
			gui.put(11, INFO, Player::closeInventory);
			gui.put(12, ACTIONS, p -> p.chat("/actions " + name));
			((Player) sender).openInventory(gui.getInventory());
		}
	}
}
