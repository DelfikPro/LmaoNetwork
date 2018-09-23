package pro.delfik.lmao.command;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.Connect;
import implario.net.packet.PacketRead;
import implario.net.packet.PacketWrite;
import implario.util.FileConverter;
import implario.util.Rank;

import java.io.File;

public class CommandSchem extends LmaoCommand {
	public CommandSchem() {
		super("sch", Rank.ULTIBUILDER, "Управление облаком схем");
	}
	@Override
	protected void run(CommandSender sender, String command, String[] args) {
		requireArgs(args, 2, "[get|upload] [Схема]");
		if (args[0].equals("get")) {
			Connect.send(new PacketRead("schematics/" + args[1] + ".schematic", "plugins/WorldEdit/schematics/" + args[1] + ".schematic"));
			sender.sendMessage("§aЗапрос на получение §f" + args[1] + ".schematic §aиз облака отправлен.");
		} else if (args[0].equals("upload")) {
			Connect.send(new PacketWrite("schematics/" + args[1] + ".schematic", FileConverter.read(new File("plugins/WorldEdit/schematics/" + args[1] + ".schematic"))));
			sender.sendMessage("§aСхема §f" + args[1] + "§a успешно загружена в облако.");
		}
	}
}
