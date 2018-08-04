package pro.delfik.lmao.core.connection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pro.delfik.lmao.core.OnlineHandler;
import pro.delfik.lmao.core.Person;
import pro.delfik.net.Packet;
import pro.delfik.net.packet.PacketAuth;
import pro.delfik.net.packet.PacketGC;
import pro.delfik.net.packet.PacketPex;
import pro.delfik.net.packet.PacketUser;
import pro.delfik.net.packet.PacketWrite;
import pro.delfik.util.FileConverter;

import java.io.File;

public class PacketListener implements Listener {
	@EventHandler
	public void event(PacketEvent event) {
		Packet packet = event.getPacket();
		if(packet instanceof PacketUser){
			PacketUser user = (PacketUser) packet;
			OnlineHandler.waitingPackets.add(user.getNick().toLowerCase(), user);
		}else if(packet instanceof PacketAuth){
			Person p = Person.get(((PacketAuth) packet).getNick());
			if (p != null) p.auth();
		}else if(packet instanceof PacketPex){
			PacketPex pex = (PacketPex)packet;
			Person.get(pex.getNick()).setRank(pex.getRank());
		}else if(packet instanceof PacketWrite){
			PacketWrite write = (PacketWrite)event.getPacket();
			FileConverter.write(new File(System.getProperty("user.dir") + "/" + write.getName()), write.getFile());
			if(!write.getName().endsWith(".jar")) return;
			Bukkit.broadcastMessage("§aНа сервер успешно загружено обновление.");
			Bukkit.reload();
		}else if(packet instanceof PacketGC){
			Runtime.getRuntime().gc();
			System.gc();
			Bukkit.broadcastMessage("§cСервер очищается от хлама. Возможна перезагрузка");
			if(((PacketGC) packet).isRl())
				Bukkit.reload();
		}
	}
}
