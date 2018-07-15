package pro.delfik.lmao.core.connection;

import pro.delfik.lmao.permissions.Core;
import pro.delfik.net.Listener;
import pro.delfik.net.P2P;
import pro.delfik.net.Packet;
import pro.delfik.util.Converter;
import pro.delfik.util.CryptoUtils;
import pro.delfik.util.Scheduler;

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

	static{
		Map<String, String> config = Converter.deserializeMap(read("config.txt"), "\n", "/");
		host = config.get("host");
		port = Converter.toInt(config.get("port"));
		utils = new CryptoUtils(config.get("key"));
	}

	public static void init(){
		try{
			new P2P(new Socket(host, port), utils, connect);
		}catch (IOException ex){
			Scheduler.sleep(1000);
			init();
		}
	}

	@Override
	public void on(P2P p2P) {

	}

	@Override
	public void update(Packet packet) {

	}

	@Override
	public void off() {

	}

	private static String prefix = System.getProperty("user.dir") + "/Core/";

	public static String read(String strFile){
		BufferedReader reader = null;
		File file = getFile(strFile);
		if(!file.exists())return null;
		StringBuffer buffer = new StringBuffer((int)file.length());
		try{
			reader = new BufferedReader(new FileReader(file));
			while (true){
				int i = reader.read();
				if(i == -1)break;
				buffer.append((char)i);
			}
		}catch (IOException ex){}
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
		}catch (IOException ex){}
	}
}
