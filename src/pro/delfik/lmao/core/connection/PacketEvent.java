package pro.delfik.lmao.core.connection;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pro.delfik.net.Packet;

public class PacketEvent extends Event {

	public static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private final Packet packet;

	PacketEvent(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}
}
