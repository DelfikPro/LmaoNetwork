package pro.delfik.lmao.core.connection.handle;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.core.connection.database.ServerIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketEvent extends Event {
	public static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	private static ServerSocket read;
	
	private final String channel;
	private final String msg;
	
	private SocketEvent(String channel, String msg) {
		this.channel = channel;
		this.msg = msg;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public static void close() {
		try {
			read.close();
		} catch (Exception ignored) {
		}
	}
	
	public static void listening() {
		try {
			String s = ServerIO.connect("getport " + Bukkit.getMotd());
			int i = (int) s.charAt(0);
			read = null;
			System.out.println(i);
			try {
				read = new ServerSocket(i);
				while (true) {
					Socket socket = read.accept();
					if (socket == null) continue;
					Bukkit.getScheduler().runTask(Lmao.plugin, new a(socket));
				}
			} catch (Exception ex) {
				try {
					Bukkit.broadcastMessage(ex.getMessage());
					read.close();
				} catch (Exception ignored) {
				}
			}
			try {
				read.close();
			} catch (Exception ignored) {
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static class a implements Runnable {
		
		private final Socket socket;
		
		public a(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String s[] = ServerIO.getCrypto().decrypt(reader.readLine()).split("\n");
				Bukkit.getPluginManager().callEvent(new SocketEvent(s[0], s[1]));
				socket.close();
			} catch (Exception ex) {
				Bukkit.broadcastMessage(ex.getMessage());
				try {
					socket.close();
				} catch (IOException ignored) {
				}
			}
		}
	}
}
