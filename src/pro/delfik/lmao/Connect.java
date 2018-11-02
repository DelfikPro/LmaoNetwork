package pro.delfik.lmao;

import __google_.net.Response;
import __google_.util.FileIO;
import implario.net.*;
import implario.util.Coder;
import org.bukkit.Bukkit;
import implario.net.packet.PacketInit;
import implario.util.Scheduler;
import pro.delfik.lmao.ev.added.PacketEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.Socket;

public class Connect implements NetListener {
	private static Connector connect;

	public static void init() {
		try{
			Socket socket = new Socket("localhost", Coder.toInt(FileIO
					.read("/Minecraft/_GLOBAL/config.txt").split("\n")[0]));
			socket.setSoTimeout(1000000000);
			connect = new Connector(socket, new Connect());
			send(new PacketInit(Bukkit.getMotd()));
		}catch (IOException ex){
			Scheduler.sleep(1000);
			init();
		}
	}

	public static void send(Packet packet){
		connect.write(new Response(0, packet.zip()));
	}

	public static void close(){
		connect.close();
	}

	@Override
	public void accept(__google_.net.Response response, Connector connector) {
		Packet packet = Packet.getPacket(response.getContent());
		Bukkit.getScheduler().runTask(Lmao.plugin, () ->
				Bukkit.getPluginManager().callEvent(new PacketEvent(packet)));
	}

	@Override
	public void closed() {
		Scheduler.sleep(1000);
		init();
	}

	private static String prefix = System.getProperty("user.dir") + "/Core/";

	public static String read(String strFile){
		BufferedReader reader = null;
		File file = getFile(strFile);
		if(!file.exists())return null;
		StringBuilder buffer = new StringBuilder((int)file.length());
		try{
			reader = new BufferedReader(new FileReader(file));
			while (true){
				int i = reader.read();
				if(i == -1)break;
				if(i == 13)continue;
				buffer.append((char)i);
			}
		}catch (IOException ignored){}
		close(reader);
		return buffer.toString();
	}

	private static File getFile(String file){
		return new File(prefix + file);
	}

	private static void close(Reader reader){
		if(reader == null)return;
		try{
			reader.close();
		}catch (IOException ignored){}
	}
}
