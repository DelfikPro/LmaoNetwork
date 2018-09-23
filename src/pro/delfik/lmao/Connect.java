package pro.delfik.lmao;

import org.bukkit.Bukkit;
import implario.net.Listener;
import implario.net.P2P;
import implario.net.Packet;
import implario.net.packet.PacketInit;
import implario.util.Converter;
import implario.util.CryptoUtils;
import implario.util.Scheduler;
import pro.delfik.lmao.ev.added.PacketEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.Socket;
import java.util.Map;

public class Connect implements Listener{

	private static Connect connect = new Connect();

	private static String host;

	private static int port;

	private static CryptoUtils utils;

	private static boolean closed = false;

	public static void init() {
		Map<String, String> config = Converter.deserializeMap(read("config.txt"), "\n", "/");
		host = config.get("host");
		port = Converter.toInt(config.get("port"));
		utils = new CryptoUtils(config.get("key"));
		try{
			Socket socket = new Socket(host, port);
			socket.setSoTimeout(1000000000);
			new P2P(socket, utils, connect);
		}catch (IOException ex){
			Scheduler.sleep(1000);
			init();
		}
	}

	public static void send(Packet packet){
		connect.p2p.send(packet);
	}

	public static void close(){
		closed = true;
		connect.p2p.close();
	}

	private P2P p2p;

	@Override
	public void on(P2P p2p) {
		this.p2p = p2p;
		p2p.send(new PacketInit(Bukkit.getMotd()));
	}

	@Override
	public void update(Packet packet) {
		Bukkit.getScheduler().runTask(Lmao.plugin, () ->
		Bukkit.getPluginManager().callEvent(new PacketEvent(packet)));
	}

	@Override
	public void off() {
		if(!closed) init();
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
