package pro.delfik.lmao.core.connection;

import implario.net.Packet;
import implario.net.packet.PacketAuth;
import implario.net.packet.PacketChangeTheme;
import implario.net.packet.PacketGC;
import implario.net.packet.PacketPex;
import implario.net.packet.PacketUser;
import implario.net.packet.PacketWrite;
import implario.util.FileConverter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pro.delfik.lmao.core.OnlineHandler;
import pro.delfik.lmao.core.Person;

import java.io.File;

public class PacketListener implements Listener {
	@EventHandler
	public void event(PacketEvent event) {
		Packet packet = event.getPacket();

		if (packet instanceof PacketUser) {
			PacketUser user = (PacketUser) packet;
			OnlineHandler.waitingPackets.add(user.getUserInfo().name.toLowerCase(), user);

		} else if (packet instanceof PacketAuth) {
			String nick = ((PacketAuth) packet).getNick();
			Person p = Person.get(nick);
			if (p != null) p.auth();

		} else if (packet instanceof PacketPex) {
			PacketPex pex = (PacketPex) packet;
			Person.get(pex.getNick()).setRank(pex.getRank());

		} else if (packet instanceof PacketWrite) {
			PacketWrite write = (PacketWrite) event.getPacket();
			FileConverter.write(new File(System.getProperty("user.dir") + "/" + write.getName()), write.getFile());
			if (!write.getName().endsWith(".jar")) return;
			Bukkit.broadcastMessage("§aНа сервер успешно загружено обновление.");
			Bukkit.reload();

		} else if (packet instanceof PacketGC) {
			Bukkit.broadcastMessage("§cСервер очищается от хлама. Возможна перезагрузка");
			Runtime.getRuntime().gc();
			if (((PacketGC) packet).isRl())
				Bukkit.reload();

		} else if (packet instanceof PacketChangeTheme) {
			Person p = Person.get(((PacketChangeTheme) packet).player);
			p.setDarkTheme(((PacketChangeTheme) packet).dark);
		}
	}
}
