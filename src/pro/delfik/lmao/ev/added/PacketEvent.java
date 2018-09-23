package pro.delfik.lmao.ev.added;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import implario.net.Packet;

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

	public PacketEvent(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}
}
