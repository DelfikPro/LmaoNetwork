package pro.delfik.lmao.anticheat;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.core.Person;
import pro.delfik.util.Rank;

public class AntiKillaura extends ImplarioCommand implements Listener {
    
    // private static final HashMap<Player, BufferedImage> images = new HashMap<>();
    @Command(name = "ak", rankRequired = Rank.DEV, description = "Проверка игрока на читы", usage = "ak test [Игрок] [Время]")
    public boolean onCommand(CommandSender sender, String s, String[] args) {
        final String pr = "§8[§cAK§8] §6";
        if (args.length == 0) {
            sender.sendMessage(pr + "/ak test [Игрок] [Время]");
            return true;
        }
        if (args[0].equals("test")) {
            try {
                Person victim = Person.get(args[1]);
                if (victim == null) {sender.sendMessage(pr + "§cИгрок не найден."); return false;}
                KillauraTest test = new KillauraTest(victim, Integer.parseInt(args[2]), sender.getName());
                test.start();
                sender.sendMessage(pr + "§aТестируем игрока §e" + victim.getDisplayName() + "§a в течение " + args[2] + " с.");
                return true;
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
                sender.sendMessage(pr + "/ak test [Игрок] [Время]");
                return true;
            }
        }



//        if (args.length != 0) {
//            File outputfile = new File(sender.getName() + ".png");
//            if (outputfile.exists()) outputfile.delete();
//            try {
//                ImageIO.write(images.get((Player) sender), "png", outputfile);
//            } catch (IOException e1) {
//                Bukkit.broadcastMessage("§6Там IOException, пойди глянь");
//                e1.printStackTrace();
//            }
//            images.remove((Player) sender);
//            infos.remove((Player) sender);
//            sender.sendMessage("§6Запись остановленааа!!!");
//            return true;
//        }
//
//        BufferedImage i = new BufferedImage(360, 180, 1);
//        images.put((Player) sender, i);
//        infos.put((Player) sender, new StringBuilder());
//        sender.sendMessage("§aЗапись пошлаааааа!!!");
        return true;
    }
    
    
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        // if (!images.containsKey(e.getPlayer())) return;
        // Graphics g = images.get(e.getPlayer()).getGraphics();
        KillauraTest t = KillauraTest.get(e.getPlayer());
        if (t == null) return;
        
        int yaw1 = ((int) (e.getFrom().getYaw())) % 360 + 180;
        int yaw2 = ((int) (e.getTo().getYaw())) % 360 + 180;
        int a = Math.abs(yaw1 - yaw2);
        if (a >= 180) a = -a + 360;
        int pitch1 = ((int) e.getFrom().getPitch()) % 90 + 45;
        int pitch2 = ((int) e.getTo().getPitch()) % 90 + 45;
        
        int d = (int) Math.hypot(a, Math.abs(pitch2 - pitch1));
        
        t.addResult(d);
        
        //g.drawLine((int) (e.getFrom().getYaw() < 0 ? e.getFrom().getYaw() + 360 : e.getFrom().getYaw()), (int) (e.getFrom().getPitch() + 91), (int) (yaw), (int) (loc.getPitch() + 91));
    }
}
