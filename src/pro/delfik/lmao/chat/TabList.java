package pro.delfik.lmao.chat;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

//TODO на банж
public class TabList {
	
	private static final PacketPlayOutPlayerListHeaderFooter packet;
	static {
		IChatBaseComponent header = ChatSerializer.a("{\"text\":\"" + "§a Вы находитесь в кластере §eLMAO/" + Bukkit.getMotd() + " §a §a\"}");
		IChatBaseComponent footer = ChatSerializer.a("{\"text\":\"" + "§aФорум сервера: §elmaonetwork.ru\n§aГруппа сервера: §evk.com/lmaonetwork\"}");
		packet = new PacketPlayOutPlayerListHeaderFooter();
		try {
			Field a = packet.getClass().getDeclaredField("a");
			a.setAccessible(true);
			a.set(packet, header);
			a.setAccessible(false);
			Field b = packet.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(packet, footer);
			b.setAccessible(false);
		} catch (NoSuchFieldException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void send(Player p) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
