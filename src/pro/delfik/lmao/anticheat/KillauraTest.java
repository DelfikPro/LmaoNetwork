package pro.delfik.lmao.anticheat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pro.delfik.lmao.core.Lmao;
import pro.delfik.lmao.core.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class KillauraTest {
    private final Person p;
    private int seconds;
    private final String tester;
    private List<Result> results = new ArrayList<>();
    
    private static final HashMap<String, KillauraTest> running = new HashMap<>();
    
    public KillauraTest(Person p, int seconds, String tester) {
        this.p = p;
        this.seconds = seconds;
        this.tester = tester;
    }
    
    public void start() {
        running.put(p.getName(), this);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Lmao.plugin, () -> {
            running.remove(p.getName());
            Person sender = Person.get(tester);
            sender.sendMessage("§dРезультаты: §7(н - нормально, с - сомнительно, к - киллаура, - - не крутит камерой):");
            StringBuilder[] sb = new StringBuilder[results.size()];
            for (int i = 0; i < results.size(); i++) {
                if (sb[i/10] == null) sb[i/10] = new StringBuilder();
                sb[i / 10].append(results.get(i).getType().toString()).append(results.get(i).getDistance());
            }
            for (StringBuilder s : sb) if (s != null) sender.sendMessage(s + "");
        }, seconds * 20);
    }
    
    public Person getPerson() {
        return p;
    }
    
    public static Collection<KillauraTest> getRunning() {
        return running.values();
    }
    
    public static KillauraTest get(Player player) {
        return running.get(player.getName());
    }
    
    public void addResult(ResultType r) {
        results.add(new Result(r));
    }
    
    public List<Result> getResults() {
        return results;
    }
    
    public void addResult(double d) {
        if (d < 3) results.add(new Result(d, KillauraTest.ResultType.NONMOVING));
        if (d >= 3 && d <= 50) results.add(new Result(d, KillauraTest.ResultType.NORMAL));
        if (d > 50 && d < 110) results.add(new Result(d, KillauraTest.ResultType.DOUGHT));
        if (d >= 110) results.add(new Result(d, KillauraTest.ResultType.KILLAURA));
    }
    
    public static class Result {
        private long millis;
        private ResultType type;
        private double d = 0;
        
        public Result(double d, ResultType type) {
            this(type);
            this.d = d;
        }
        
        public double getDistance() {
            return d;
        }
        
        public Result(ResultType type) {
            this.millis = System.currentTimeMillis();
            this.type = type;
        }
        
        public long getMillis() {
            return millis;
        }
        
        public ResultType getType() {
            return type;
        }
    }
    
    public enum ResultType {
        NONMOVING("§7 "), NORMAL("§a "), DOUGHT("§e "), KILLAURA("§c ");
        
        private String string;
        ResultType(String string) {
            this.string = string;
        }
        
        @Override
        public String toString() {
            return string;
        }
    }
}
