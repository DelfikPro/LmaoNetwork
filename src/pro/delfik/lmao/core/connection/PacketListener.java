package pro.delfik.lmao.core.connection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pro.delfik.lmao.core.OnlineHandler;
import pro.delfik.net.Packet;
import pro.delfik.net.packet.PacketUser;

import java.util.HashMap;

public class PacketListener implements Listener {
	@EventHandler
	public void event(PacketEvent event) {
		Packet packet = event.getPacket();
		if(packet instanceof PacketUser){
			PacketUser user = (PacketUser) packet;
			OnlineHandler.waitingPackets.add(user.getNick().toLowerCase(), user);
		}
	}
}
