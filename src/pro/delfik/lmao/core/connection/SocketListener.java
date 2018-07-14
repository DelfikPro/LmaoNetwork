package pro.delfik.lmao.core.connection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pro.delfik.lmao.core.User;
import pro.delfik.lmao.core.connection.handle.SocketEvent;
import pro.delfik.lmao.permissions.Core;
import pro.delfik.lmao.permissions.Rank;

import java.io.File;
import java.util.ArrayList;

public class SocketListener implements Listener {
	@EventHandler
	public void event(SocketEvent event) {
		switch (event.getChannel()) {
			case "pex":
				String s[] = event.getMsg().split("/");
				Rank rank = Rank.decode(s[1]);
				if (rank == null) rank = Rank.PLAYER;
				User.getUser(s[0]).setRank(rank);
				break;
			case "stp":
				new a(event).start();
				break;
			case "update":
				ArrayList<String> array = new ArrayList<>();
				for (File file : new File(System.getProperty("user.dir") + "/plugins").listFiles())
					array.add(file.getName());
				if (array.contains(event.getMsg()))
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "update " + event.getMsg());
				break;
			case "auth":
				Core.players.remove(event.getMsg());
				break;
		}
//            String split[] = event.getMsg().split("/");
//            if(split.length != 2)return;
//            ServerControl.addServer(split[0], ServerType.valueOf(split[1]));
//        } else if(event.getChannel().equals("remServer")){
//            ServerControl.delServer(event.getMsg());
//        } else if(event.getChannel().equals("user")){
//            String split[] = event.getMsg().split("/");
//            if (split.length != 2) return;
//            Server server = ServerControl.getServer(split[1]);
//            User user = User.getUser(split[0]);
//            if (user == null) return;
//            user.setServer(server);
//        }
	}
	
	private static class a extends Thread {
		
		private final SocketEvent event;
		
		public a(SocketEvent event) {
			this.event = event;
		}
		
		@Override
		public void run() {
			try {
				sleep(500);
			} catch (InterruptedException ignored) {
			}
			String s[] = event.getMsg().split("/");
			if (s.length != 2) return;
			Player player = Bukkit.getPlayer(s[0]);
			if (player == null) return;
			Player player1 = Bukkit.getPlayer(s[1]);
			if (player1 == null) return;
			player.teleport(player1);
		}
	}
}
