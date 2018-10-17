package pro.delfik.lmao.command;

import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.outward.Generate;
import pro.delfik.lmao.outward.item.ItemBuilder;
import pro.delfik.lmao.outward.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;
import implario.util.Rank;

@Cmd(name = "player", description = "Меню с информацией об игроке")
public class CmdPlayer extends LmaoCommand {
	private static final ItemStack STATS = Generate.itemstack(Material.ITEM_FRAME, 1, 0, "§f>> §6§lСтатистика §f<<", "§e§oПодробная статистика по всем режимам");
	private static final ItemStack ACTIONS = Generate.charge(Color.YELLOW,"§f>> §6§lДействия §f<<", "§e§oПосмотреть доступные действия с игроком");
	
	@Override
	public void run(Person person, String args[]) {
		String name = args.length == 0 ? person.getName() : args[0];
		Person v = Person.get(name);
		if (v == null) {
			person.sendMessage("§6Скоро можно будет проверять и оффлайн-игроков.");
		} else {
			GUI gui = new GUI(Bukkit.createInventory(null,27,"§0§lИгрок " + name));
			gui.put(10, STATS, player -> {
				player.closeInventory();
				player.sendMessage("§eВ разработке.");
			});
			gui.put(11, generateOnlineItem(v), null);
			gui.put(12, generateMoneyItem(v), null);
			gui.put(16, ACTIONS, p -> p.chat("/actions " + name));
			person.getHandle().openInventory(gui.getInventory());
		}
	}
	
	private static ItemStack generateOnlineItem(Person v) {
		String time = representTime(v.getOnlineTime());
		return new ItemBuilder(Material.WATCH).withDisplayName("§fВремя, проведённое в игре:").withLore("§e" + time).build();
	}
	
	private static ItemStack generateMoneyItem(Person v) {
		return new ItemBuilder(Material.DOUBLE_PLANT).withDisplayName("§fКоличество монеток:").withLore("§e" + v.getMoney()).build();
	}
	
	private static String representTime(long time) {
		StringBuilder b = new StringBuilder();
		time /= 1000;
		long days = time / 86400;
		time -= days * 86400;
		long hours = time / 3600;
		time -= hours * 3600;
		long minutes = time / 60;
		time -= minutes * 60;
		return b.append(days).append(" д. ").append(hours).append(" ч. ").append(minutes).append(" м. ").append(time).append(" с.").toString();
	}
}
