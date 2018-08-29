package pro.delfik.lmao.outward;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Texteria implements Listener {
	
	private Texteria() {}
	
	public static Text create(Location loc, String[] text) {
		List<ArmorStand> a = new ArrayList<>();
		for (String string : text) {
			ArmorStand e = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
			e.setCustomName(string);
			e.setCustomNameVisible(true);
			e.setGravity(false);
			e.setSmall(true);
			e.setVisible(false);
			e.setRemoveWhenFarAway(false);
			loc.setY(loc.getY() - 0.25);
			a.add(e);
		}
		return new Text(a, text);
	}
	
	public static boolean remove(int id) {
		Text t = Text.get(id);
		if (t == null) return false;
		t.remove();
		return true;
	}
	
	public static List<Text> getTextList() {
		return Text.list;
	}
	
	public static class Text {
		private List<ArmorStand> stands;
		private int id;
		private String[] lines;
		
		public static List<Text> list = new ArrayList<>();
		
		public Text(List<ArmorStand> stands, String[] lines) {
			this.stands = stands;
			this.id = list.size() + 1;
			this.lines = lines;
			list.add(this);
		}
		public String[] getLines() {
			return lines;
		}
		
		public void setLines(String[] lines) {
			if (lines.length > this.lines.length) throw new IllegalArgumentException("Превышено количество строчек");
			Iterator<ArmorStand> it = stands.iterator();
			for (String line : lines) it.next().setCustomName(line);
			this.lines = lines;
		}
		
		public List<ArmorStand> getStands() {
			return stands;
		}
		public int getID() {
			return id;
		}
		
		public void remove() {
			for (ArmorStand a : stands) {
				a.remove();
			}
		}
		
		public static Text get(int id) {
			for (Text t : list) if (t.getID() == id) return t;
			return null;
		}
	}
}
