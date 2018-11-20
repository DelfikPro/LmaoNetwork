package pro.delfik.lmao.outward.texteria;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import pro.delfik.lmao.Lmao;
import pro.delfik.lmao.outward.texteria.utils.ByteMap;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Texteria
implements Listener,
PluginMessageListener {
    private static Map<Player, Queue<byte[]>> buffer = new LinkedHashMap<>();
    private final int nettyThreads = Integer.parseInt(System.getProperty("io.netty.eventLoopThreads", "0"));
    private final Map<EventLoop, List<Object[]>> sendQueueMap = new LinkedHashMap<>();
    private final LinkedList<Object[]> sendQueueList = new LinkedList();

    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(Lmao.plugin, "Texteria");
        Bukkit.getMessenger().registerIncomingPluginChannel(Lmao.plugin, "Texteria", this);
        Bukkit.getPluginCommand("texteria").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(this, Lmao.plugin);
        for (Player player : Bukkit.getOnlinePlayers()) this.onPlayerJoin(new PlayerJoinEvent(player, null));
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Lmao.plugin, () -> {
            if (buffer.isEmpty()) {
                return;
            }
            if (this.nettyThreads == 1) {
                this.sendQueueList.clear();
            } else {
                this.sendQueueMap.clear();
            }
            buffer.forEach((player, queue) -> {
                if (queue.isEmpty()) {
                    return;
                }
                int size = 2;
                ArrayList<byte[]> list = new ArrayList<>(queue.size());
                byte[] temp = queue.poll();
                while (temp != null) {
                    list.add(temp);
                    size += 4 + temp.length;
                    temp = queue.poll();
                }
                PacketDataSerializer data = new PacketDataSerializer(Unpooled.buffer(size));
                data.b(list.size());
                for (byte[] arr : list) {
                    data.b(arr.length);
                    data.writeBytes(arr);
                }
                PlayerConnection conn = ((CraftPlayer)player).getHandle().playerConnection;
                if (conn != null) {
                    Object[] toSend = new Object[]{conn, new PacketPlayOutCustomPayload("Texteria", data)};
                    if (this.nettyThreads == 1) {
                        this.sendQueueList.add(toSend);
                    } else {
						Channel c;
						try {
							Field f = conn.networkManager.getClass().getDeclaredField("i");
							f.setAccessible(true);
							c = (Channel) f.get(conn.networkManager);
						} catch (NoSuchFieldException | IllegalAccessException e) {
							throw new RuntimeException(e);
						}
						this.sendQueueMap.computeIfAbsent(c.eventLoop(), l -> new LinkedList()).add(toSend);
                    }
                }
            });
            if (this.nettyThreads == 1) {
                if (this.sendQueueList.isEmpty()) {
                    return;
                }
				NetworkManager nm = ((PlayerConnection) this.sendQueueList.getFirst()[0]).networkManager;
				Channel c;
				try {
					Field f = nm.getClass().getDeclaredField("i");
					f.setAccessible(true);
					c = (Channel) f.get(nm);
				} catch (NoSuchFieldException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				EventLoop loopGroup = c.eventLoop();
                loopGroup.execute(() -> this.sendQueueList.forEach(data -> ((PlayerConnection)data[0]).sendPacket((Packet)data[1])));
                this.sendQueueList.clear();
            } else {
                if (this.sendQueueMap.isEmpty()) {
                    return;
                }
                for (Map.Entry<EventLoop, List<Object[]>> entry : this.sendQueueMap.entrySet()) {
                    List<Object[]> value = entry.getValue();
                    entry.getKey().execute(() -> value.forEach(data -> ((PlayerConnection)data[0]).sendPacket((Packet)data[1])));
                }
                this.sendQueueMap.clear();
            }
        }, 1L, 1L);
    }

    @EventHandler(priority=EventPriority.LOWEST)
    private void onPlayerJoin(PlayerJoinEvent event) {
        buffer.put(event.getPlayer(), new ConcurrentLinkedQueue());
        ((CraftPlayer)event.getPlayer()).addChannel("Texteria");
        Texteria2D.removeAll(event.getPlayer());
        Texteria3D.removeAllGroups(event.getPlayer());
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        buffer.remove(event.getPlayer());
    }

    @EventHandler
    private void onPlayerKick(PlayerKickEvent event) {
        buffer.remove(event.getPlayer());
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (channel.equals("Texteria")) {
            ByteMap map = new ByteMap(data);
            switch (map.getString("%", "")) {
                case "callback": {
                    Bukkit.getPluginManager().callEvent(new TexteriaCallbackEvent(player, map.getMap("data")));
                }
            }
        }
    }

    public static /* varargs */ void openUrl(String url, Player ... players) {
        Texteria.sendPacket(Texteria.buildOpenUrl(url), players);
    }

    public static void openUrl(String url, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria.buildOpenUrl(url), players);
    }

    private static byte[] buildOpenUrl(String url) {
        ByteMap map = new ByteMap();
        map.put("%", "url");
        map.put("url", url);
        return map.toByteArray();
    }

    public static void sendPacket(byte[] bytes, Collection<? extends Player> players) {
        players.forEach(player -> {
            Queue<byte[]> queue = buffer.get(player);
            if (queue != null) {
                queue.add(bytes);
            }
        });
    }

    public static void sendPacket(byte[] bytes, Player[] players) {
        for (Player player : players) {
            Queue<byte[]> queue = buffer.get(player);
            if (queue == null) continue;
            queue.add(bytes);
        }
    }
}

