package pro.delfik.lmao.command;

import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.Connect;
import implario.net.packet.PacketRead;
import implario.net.packet.PacketWrite;
import implario.util.FileConverter;
import implario.util.Rank;
import pro.delfik.lmao.user.Person;

import java.io.File;

@Cmd(name = "sch", rank = Rank.ULTIBUILDER, description = "Управление облаком схем", args = 2, help = "[get|upload] [Схема]")
public class CmdSchem extends LmaoCommand {
	@Override
	public void run(Person person, String args[]) {
		if (args[0].equals("get")) {
			Connect.send(new PacketRead("schematics/" + args[1] + ".schematic", "plugins/WorldEdit/schematics/" + args[1] + ".schematic"));
			person.sendMessage("§aЗапрос на получение §f" + args[1] + ".schematic §aиз облака отправлен.");
		} else if (args[0].equals("upload")) {
			File file = new File("plugins/WorldEdit/schematics/" + args[1] + ".schematic");
			if(!file.exists()) file = new File("plugins/WorldEdit/schematics/" + person.getHandle().getUniqueId() + "/" + args[1] + ".schematic");
			Connect.send(new PacketWrite("schematics/" + args[1] + ".schematic", FileConverter.read(file)));
			person.sendMessage("§aСхема §f" + args[1] + "§a успешно загружена в облако." + person.getHandle().getUniqueId());
		}
	}
}
