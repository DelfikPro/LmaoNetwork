package pro.delfik.lmao.core.connection.database;

import org.bukkit.Bukkit;
import pro.delfik.lmao.core.connection.database.objects.DataList;
import pro.delfik.lmao.core.connection.database.objects.DataObject;
import pro.delfik.lmao.util.CryptoUtils;
import pro.delfik.lmao.util.ServerType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;

public class ServerIO {
	
	private static String host;
	private static int port;
	
	private static CryptoUtils crypto;
	
	public static boolean isConnect() {
		return true;
	}
	
	static {
		DataList list = LocalIO.read("config");
		if (list == null) {
			System.out.println("Error, file config not found");
			Bukkit.shutdown();
			host = null;
			port = -1;
		} else {
			DataObject object = list.get("host");
			if (object == null) {
				System.out.println("Error, needed host from host/ip_database");
				Bukkit.shutdown();
				host = null;
				port = -1;
			} else {
				host = object.getValue();
				object = list.get("port");
				if (object == null) {
					System.out.println("Error, needed ip from port/port_database");
					Bukkit.shutdown();
					port = -1;
				} else {
					port = Integer.decode(object.getValue());
				}
			}
		}
		list = LocalIO.read("crypto");
		String key;
		if (list == null) {
			System.out.println("Error, needed file crypto from key");
			Bukkit.shutdown();
			key = null;
		} else {
			DataObject object = list.get("key");
			if (object == null) {
				System.out.println("Error, needed key from format = key/Magic key from size 16, 24 or 32");
				Bukkit.shutdown();
				key = null;
			} else {
				String s = object.getValue();
				if (s.length() != 16 && s.length() != 24 && s.length() != 32) {
					System.out.println("Error, needed key size 16, 24 or 32");
					Bukkit.shutdown();
					key = null;
				} else {
					key = s;
				}
			}
		}
		crypto = new CryptoUtils(key);
	}
	
	public static CryptoUtils getCrypto() {
		return crypto;
	}
	
	public static DataList read(String path) {
		return a(path, "get");
	}
	
	public static DataList getAll(String path) {
		return a(path, "getall");
	}
	
	private static DataList a(String path, String key) {
		String s = connect(key + " " + path);
		if (s == null || s.equals("null\n")) return null;
		String s2[] = s.split("\n");
		DataList list = new DataList(s2.length);
		for (String s1 : s2) {
			String s3[] = s1.split("/");
			if (s3.length != 2) continue;
			list.put(s3[0], new DataObject(s3[1].replaceAll("\u1235", " ")));
		}
		return list;
	}
	
	public static void create(DataList list, String path) {
		String keys[] = list.getAllKeys();
		DataObject objects[] = list.getAllValues();
		StringBuilder builder = new StringBuilder();
		try {
			for (int i = 0; i < keys.length; i++)
				builder.append(" ").append(keys[i]).append('/').append(objects[i].getValue().replaceAll(" ", "\u1235"));
		} catch (NullPointerException ignored) {
		}
		connect("write " + path + builder.toString());
	}
	
	public static void remove(String path) {
		connect("remove " + path);
	}
	
	public static String pex(String nick) {
		return connect("pex " + nick);
	}
	
	public static String memGet(String key) {
		return connect("memget " + key);
	}
	
	public static void memSet(String key, String value) {
		connect("memset " + key + " " + value);
	}
	
	public static void memRem(String key) {
		connect("memrem " + key);
	}
	
	public static void event(String channel, String message) {
		connect("broadevent " + channel + " " + message);
	}
	
	public static void event(String channel, String message, String server) {
		connect("event " + server + " " + channel + " " + message);
	}
	
	public static void bungeeEvent(String channel, String message) {
		connect("bungeeevent " + channel + " " + message);
	}
	
	public static void typeEvent(ServerType type, String channel, String message) {
		connect("typeevent " + type + " " + channel + " " + message);
	}
	private static final Collection<String> nonVoid = Arrays.asList("get", "pex", "mem", "rea", "ser");
	public static String connect(String message) {
		try {
			Socket socket = new Socket(host, port);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			String crypt = crypto.encrypt(message);
			writer.write(crypt + "\n");
			writer.flush();
			if (nonVoid.contains(message.substring(0, 3))) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String sb = reader.readLine();
				socket.close();
				return crypto.decrypt(sb);
			}
			socket.close();
		} catch (Throwable ex) {
			System.out.println("Скорее всего неверный ключ");
			ex.printStackTrace();
		}
		return null;
	}
}
