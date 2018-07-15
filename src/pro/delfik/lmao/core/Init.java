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
import pro.delfik.lmao.core.connection.PacketListener;
import pro.delfik.lmao.core.connection.PacketEvent;
import pro.delfik.lmao.misc.Garpoon;
import pro.delfik.lmao.misc.Invseer;
import pro.delfik.lmao.permissions.Authenticator;

public class Init {
	
	public static Registrar r;
	
	public static void events() {
		PacketEvent.class.getCanonicalName();
		r.regEvent(new ChatHandler());
		r.regEvent(new OnlineHandler());
		r.regEvent(new Garpoon());
		r.regEvent(new AntiKillaura());
		r.regEvent(new AntiClicker());
		r.regEvent(new Authenticator());
		r.regEvent(new PacketListener());
		r.regEvent(new Invseer());
	}
	
	public static void commands() {
		final ImplarioCommand[] cmds = new ImplarioCommand[] {new CommandAdmin(), new CommandGamemode(),
				new CommandHelp(), new CommandSudo(), new CommandTeleport(),
				new CommandVanish(), new AntiKillaura(), new CommandList(), new CommandControl(),
				new CommandStop(), new CommandPlayer(), new CommandActions()};
		new Thread(() -> {
			for (ImplarioCommand cmd : cmds) r.regCommand(cmd);
		}).start();
	}
}
