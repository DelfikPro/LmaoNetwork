package pro.delfik.lmao.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import pro.delfik.lmao.Lmao;
import pro.delfik.lmao.user.Person;

import java.util.List;

public class Cooldown {
	private String name;
	private int remain;
	private int task;
	private List<Person> players;
	private Runnable action;
	
	public Cooldown(String name, int seconds, List<Person> p, Runnable action) {
		this.name = name;
		this.remain = seconds;
		this.players = p;
		this.action = action;
		task = task();
	}
	
	public void cancel() {
		Bukkit.getScheduler().cancelTask(task);
		for (Person p : players == null ? Person.online() : players) {
			p.sendTitle("§f");
			p.sendSubtitle("§eОтсчёт отменён.");
		}
	}
	
	public int task() {
		
		return Bukkit.getScheduler().scheduleSyncRepeatingTask(Lmao.plugin, () -> {
			remain--;
			for (Person p : players == null ? Person.online() : players){
				p.sendTitle("§f");
				p.sendSubtitle("§a§l" + remain);
				p.getHandle().playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
			}
			if(remain <= 0){
				Bukkit.getScheduler().cancelTask(task);
				action.run();
			}
		}, 20L, 20L);
	}
	
	public String getName() {
		return name;
	}

	public int getRemain() {
		return remain;
	}
	
	public List<Person> getPlayers() {
		return players;
	}
	
	public int getTask() {
		return task;
	}
	
	public Runnable getAction() {
		return action;
	}
}
