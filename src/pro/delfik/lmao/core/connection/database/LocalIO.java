package pro.delfik.lmao.core.connection.database;

import pro.delfik.lmao.core.connection.database.io.Helper;
import pro.delfik.lmao.core.connection.database.objects.DataList;
import pro.delfik.lmao.core.connection.database.objects.DataObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LocalIO {
	
	private static final String prefix = System.getProperty("user.dir") + "/Core/";
	
	public static DataList read(String path) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(getFile(path)));
			DataList list = new DataList(1);
			while (true) {
				String s = in.readLine();
				if (s == null) break;
				String s1[] = s.split("/");
				if (s1.length != 2) continue;
				list.put(s1[0], new DataObject(s1[1]));
			}
			in.close();
			return list;
		} catch (IOException ex) {
			try {
				if (in != null) in.close();
			} catch (IOException ignored) {
			}
			return null;
		}
	}
	
	public static void write(String path, DataList list) {
		BufferedWriter out = null;
		try {
			File file = getFile(path);
			file.getParentFile().mkdirs();
			file.getParentFile().mkdir();
			file.createNewFile();
			out = new BufferedWriter(new FileWriter(file));
			String s[] = list.getAllKeys();
			DataObject o[] = list.getAllValues();
			for (int i = 0; i < s.length; i++)
				out.write(s[i] + "/" + o[i].toString() + "\n");
			out.close();
		} catch (IOException ex) {
			try {
				if (out != null) out.close();
			} catch (IOException ignored) {
			}
		}
	}
	
	public static DataList getAll(String path) {
		File file = getFile(path);
		if (file.isDirectory()) return null;
		File files[] = file.listFiles();
		if (files == null) return null;
		DataList list = new DataList(1);
		ArrayList<String> b = new ArrayList<>(files.length);
		for (File a : files) {
			b.add(a.getName());
		}
		list.put("files", new DataObject(Helper.toString(b)));
		return list;
	}
	
	public static void remove(String path) {
		getFile(path).delete();
	}
	
	private static File getFile(String path) {
		return new File(prefix + path + ".txt");
	}
}
