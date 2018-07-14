package pro.delfik.lmao.core;

import pro.delfik.lmao.anticheat.AntiClicker;
import pro.delfik.lmao.anticheat.AntiKillaura;
import pro.delfik.lmao.chat.ChatHandler;
import pro.delfik.lmao.command.CommandActions;
import pro.delfik.lmao.command.CommandAdmin;
import pro.delfik.lmao.command.CommandControl;
import pro.delfik.lmao.command.CommandGamemode;
import pro.delfik.lmao.command.CommandHelp;
import pro.delfik.lmao.command.CommandList;
import pro.delfik.lmao.command.CommandPlayer;
import pro.delfik.lmao.command.CommandStop;
import pro.delfik.lmao.command.CommandSudo;
import pro.delfik.lmao.command.CommandTeleport;
import pro.delfik.lmao.command.CommandVanish;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.core.connection.SocketListener;
import pro.delfik.lmao.core.connection.handle.SocketEvent;
import pro.delfik.lmao.misc.Garpoon;
import pro.delfik.lmao.misc.Invseer;
import pro.delfik.lmao.permissions.Authenticator;
import pro.delfik.lmao.permissions.PermsManager;
import pro.delfik.lmao.permissions.Rank;

public class Init {
	
	public static Registrar r;
	
	public static void events() {
		SocketEvent.class.getCanonicalName();
		r.regEvent(new ChatHandler());
		r.regEvent(new OnlineHandler());
		r.regEvent(new PermsManager());
		r.regEvent(new Garpoon());
		r.regEvent(new AntiKillaura());
		r.regEvent(new AntiClicker());
		r.regEvent(new Authenticator());
		r.regEvent(new SocketListener());
		r.regEvent(new Invseer());
	}
	
	public static void commands() {
		final ImplarioCommand[] cmds = new ImplarioCommand[] {new CommandAdmin(), new CommandGamemode(),
				new CommandHelp(), new CommandSudo(), new CommandTeleport(),
				new CommandVanish(), new AntiKillaura(), new CommandList(), new CommandControl(),
				new CommandStop(), new CommandPlayer(), new CommandActions()};
		new Thread(new Runnable() {@Override public void run() {
			for (ImplarioCommand cmd : cmds) r.regCommand(cmd);
			r.regCommand("update", Rank.ADMIN, "Управление обновлениями", "update [Название файла|all]");
			r.regCommand("pex", Rank.DEV, "Управление привелегиями игроков", "pex [Ник] [...]");
			r.regCommand("end", Rank.ADMIN, "Остановка главного сервера", "end [Сообщение]");
			r.regCommand("alert", Rank.DEV, "Отправить сообщение на все сервера", "alert [Сообщение]");
			r.regCommand("stp", Rank.MODER, "Телепортация на другой сервер или напрямую к игроку", "stp [Игрок|@Сервер]");
			r.regCommand("tell", Rank.PLAYER, "Отправить личное сообщение игроку", "tell [Игрок] [Сообщение]");
			r.regCommand("reply", Rank.PLAYER, "Быстрый ответ на последнее личное сообщение", "reply [Сообщение]");
			r.regCommand("hub", Rank.PLAYER, "Телепортация в лобби", "hub");
			r.regCommand("online", Rank.PLAYER, "Мониторинг онлайна серверов", "online [Игрок|@Сервер]");
			r.regCommand("ban", Rank.MODER, "Блокировка игрока", "ban [Игрок] [Время] [Причина]");
			r.regCommand("kick", Rank.MODER, "Отключение игрока от сервера", "kick [Игрок] [Причина]");
			r.regCommand("unban", Rank.MODER, "Разблокировка игрока", "unban [Игрок]");
			r.regCommand("friend", Rank.PLAYER, "Управление друзьями", "friend [add|remove|list] [...]");
			r.regCommand("ignore", Rank.PLAYER, "Добавление игрока в чёрный список", "ignore [Игрок]");
			r.regCommand("unignore", Rank.PLAYER, "Удаление игрока из чёрного списка", "unignore [Игрок]");
		}}).start();
	}
}
