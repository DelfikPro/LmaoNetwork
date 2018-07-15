package pro.delfik.lmao.core.connection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pro.delfik.lmao.core.OnlineHandler;
import pro.delfik.lmao.core.Person;
import pro.delfik.net.Packet;
import pro.delfik.net.packet.PacketAuth;
import pro.delfik.net.packet.PacketPex;
import pro.delfik.net.packet.PacketUser;

public class PacketListener implements Listener {
	@EventHandler
	public void event(PacketEvent event) {
		Packet packet = event.getPacket();
		if(packet instanceof PacketUser){
			PacketUser user = (PacketUser) packet;
			OnlineHandler.waitingPackets.add(user.getNick().toLowerCase(), user);
		}else if(packet instanceof PacketAuth){
			Person.get(((PacketAuth) packet).getNick()).auth();
		}else if(packet instanceof PacketPex){
			PacketPex pex = (PacketPex)packet;
			Person.get(pex.getNick()).setRank(pex.getRank());
		}
	}
}
