/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package pro.delfik.lmao.outward.texteria;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pro.delfik.lmao.outward.texteria.elements.*;
import pro.delfik.lmao.outward.texteria.utils.*;
import pro.delfik.lmao.outward.texteria.world.WorldGroup;

public class Commands
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/texteria group|text|rect|ptimer|rtimer|vig|vis|anim|3d");
            return true;
        }
        if (args[0].equals("group")) {
            Element[] cs = new Element[]{new Text("test.text", "eeeeeeeeee").setOffset(0, 30).setDuration(3000L), new Rectangle("test.rect", 20).setDuration(3000L)};
            Texteria2D.add(cs, (Player)sender);
            sender.sendMessage("\u041f\u0430\u043a\u0435\u0442 \u043e\u0442\u043f\u0440\u0430\u0432\u043b\u0435\u043d");
            return true;
        }
        Element elem = null;
        switch (args[0]) {
            case "text": {
                elem = new Text("test", "Test text");
                break;
            }
            case "rect": {
//				ByteMap clickData = new ByteMap();
//				clickData.put("module", "bw-tournament-ui");
//				clickData.put("action", "tp");
//				clickData.put("player", info.username);
                elem = new Rectangle("test", 50, 100).setOnClick(new OnClick(OnClick.Action.CHAT, "Я официально подтверждаю, что я даун"));
                break;
            }
			case "form": {

				Rectangle back = new Rectangle("back", 100, 50).setPosition(Position.TOP_LEFT).setColor(0xff202020)
						.setAnimation(new Animation2D().setStart(new Animation2D.Params().setX(100))).setDuration(5000);
				Rectangle okButton = new Rectangle("okBtn", 40, 10).setColor(0xfff4a142).setOffset(10, 20)
						.setAttachment(new Attachment("back", Position.CENTER))
						.setOnClick(new OnClick(OnClick.Action.CHAT, "Я согласна выйти за тебя замуж!")).setDuration(5000);
				Text okTxt = new Text("okTxt", "Принять").setColor(-1).setShadow(false).setPosition(Position.CENTER).setDuration(5000)
						.setAttachment(new Attachment("okBtn", Position.CENTER));

				Element[] c = {back, okButton, okTxt};

				Element[] cs = new Element[]{new Rectangle("test.rect", 50, 10).setDuration(10000L).setPosition(Position.TOP_LEFT).setOffset(10, 10)
						.setOnClick(new OnClick(OnClick.Action.CHAT, "Я точно крутой!")).setColor(0xff20a020).setHoverable(true),
						new Text("test.text", "Я крутой").setDuration(10000L).setPosition(Position.TOP_LEFT).setOffset(11, 10)};
				Texteria2D.add(c, (Player)sender);
				sender.sendMessage("\u041f\u0430\u043a\u0435\u0442 \u043e\u0442\u043f\u0440\u0430\u0432\u043b\u0435\u043d");
				return true;

			}
            case "ptimer": {
                elem = new ProgressTimer("test", 100, 50).setBarColor(-65536);
                break;
            }
            case "rtimer": {
                elem = new RadialProgressTimer("test", 40);
                break;
            }
            case "vig": {
                elem = new Vignette("test").setColor(-16737025);
                break;
            }
            case "vis": {
                elem = new Text("test", "\u0412\u0438\u0434\u043d\u043e \u0432\u0441\u0435\u0433\u0434\u0430").setScale(2.0f).setVisibility(Visibility.ALWAYS);
                break;
            }
            case "anim": {
                elem = new Text("test", "\u0410\u043d\u0438\u043c\u0430\u0446\u0438\u044f").setAnimation(
                		new Animation2D().setStart(
                				new Animation2D.Params().setY(10).setRotation(360.0f))
						.setFinish(
								new Animation2D.Params().setScale(20.0f))
						).setFadeStart(1000).setFadeFinish(500);
                break;
            }
            case "3d": {
                WorldGroup group = new WorldGroup("test");
                group.setDuration(10000L);
                group.setFade(500);
                group.setScale(2.0f);
                group.setLocation(-9.0f, 60.0f, -25.0f);
                group.setHoverable(true);
                group.animation.setBoth(new Animation3D.Params().setOffset(8.0f, -5.0f, 0.0f).setScale(-8.0f).setRotation(90.0f, 0.0f, 0.0f));
                group.add(
						new Table("test").setTitle("Топ долбаёбов")
								.addColumn(new Table.Column("#", 15).setCenter(true))
								.addColumn(new Table.Column("Игрок",	80))
								.addColumn(new Table.Column("Долбаебизм", 62).setColor(-7617718))
								.addRow("1", "Okssi", "80412§f%")
								.addRow("2", "DickyGaming", "281§f%")
								.addRow("3", "lakaithree", "101§f%")
								.addRow("4", "Dev-Школьники", "99§f%")
								.addRow("5", "VeselyyMedved", "95§f%")
								.addRow("6", "§7§o - EMPTY - ", "§7-")
								.addRow("7", "§7§o - EMPTY - ", "§7-")
								.addRow("8", "§7§o - EMPTY - ", "§7-")
								.addRow("9", "§7§o - EMPTY - ", "§7-")
								.addRow("10", "§7§o - EMPTY - ", "§7-")
								.addRow("11", "§7§o - EMPTY - ", "§7-")
								.addRow("12", "§7§o - EMPTY - ", "§7-")
								.addRow("13", "Гугл", "8§f%")
								.setHoverable(true));
//                group.add(new Image("vn.api.games/bw", -1, "file:texteria/games/bw.png").setPosition(Position.LEFT).setScale(0.5f));
                Texteria3D.addGroup(group, (Player)sender);
                sender.sendMessage("\u041f\u0430\u043a\u0435\u0442 \u043e\u0442\u043f\u0440\u0430\u0432\u043b\u0435\u043d");
                return true;
            }
			case "tt": {
				elem = new TextStopwatch("lolkek", "cheburek").setBackground(0xc0202020).setHoverBackground(0xc0c0c020);
				break;
			}
			case "win": {
				String id = args.length > 1 ? args[1] : "victory";
				elem = new Image("vn.api." + id, -1, "file:texteria/" + id + ".png").setPosition(Position.TOP)
						.setOffset(0, 10).setFade(1500).setScale(0.5F).setDuration(7000L);
				break;
			}

        }
        if (elem == null) {
            sender.sendMessage(ChatColor.RED + "\u0422\u044b \u0447\u0435\u0433\u043e \u043d\u0430\u043f\u0438\u0441\u0430\u043b, \u0430?");
            return true;
        }
        Texteria2D.add(elem.setDuration(3000L), (Player)sender);
        sender.sendMessage("\u041f\u0430\u043a\u0435\u0442 \u043e\u0442\u043f\u0440\u0430\u0432\u043b\u0435\u043d");
        return true;
    }
}

