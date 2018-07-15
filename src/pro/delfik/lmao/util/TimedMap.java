package pro.delfik.lmao.util;

import lib.I;

import java.util.HashMap;

public class TimedMap<K, V> {
	private final HashMap<K, V> list = new HashMap<>();
	
	private final int time;
	
	public TimedMap(int time){
		this.time = time;
	}
	
	public void add(K key, V value){
		list.put(key, value);
		I.delay(() -> remove(key), 20 * time);
	}
	
	public V get(K key) {
		return list.get(key);
	}
	
	public void remove(K key){
		list.remove(key);
	}
	
}
