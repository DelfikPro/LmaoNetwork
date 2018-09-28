package pro.delfik.lmao.outward.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Sidebar {

	private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	private final Objective objective;
	private final String title;
	private final List<String> entries = new ArrayList<>(15);

	public Sidebar(String title) {
		this.title = title;
		objective = scoreboard.registerNewObjective("obj", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(title);
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public void show(Player p) {
		p.setScoreboard(scoreboard);
	}

	public void setLine(int number) {
	}

}
