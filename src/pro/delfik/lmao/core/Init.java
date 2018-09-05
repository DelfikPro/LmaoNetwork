package pro.delfik.lmao.core;

import pro.delfik.lmao.anticheat.AntiClicker;
import pro.delfik.lmao.chat.ChatHandler;
import pro.delfik.lmao.command.CmdI;
import pro.delfik.lmao.command.CommandActions;
import pro.delfik.lmao.command.CommandAdmin;
import pro.delfik.lmao.command.CommandGamemode;
import pro.delfik.lmao.command.CommandHelp;
import pro.delfik.lmao.command.CommandList;
import pro.delfik.lmao.command.CommandPlayer;
import pro.delfik.lmao.command.CommandSchem;
import pro.delfik.lmao.command.CommandStop;
import pro.delfik.lmao.command.CommandSudo;
import pro.delfik.lmao.command.CommandTeleport;
import pro.delfik.lmao.command.CommandVanish;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.core.connection.PacketListener;
import pro.delfik.lmao.core.connection.PacketEvent;
import pro.delfik.lmao.misc.Garpoon;
import pro.delfik.lmao.misc.Invseer;
import pro.delfik.lmao.command.CommandMultipleCmds;
import pro.delfik.lmao.outward.TotalDisabler;
import pro.delfik.lmao.outward.Trampoline;
import pro.delfik.lmao.outward.gui.GUIHandler;
import pro.delfik.lmao.permissions.Authenticator;

public class Init {
	
	public static Registrar r;
	
	public static void events() {
		PacketEvent.class.getCanonicalName();
		r.regEvent(new ChatHandler());
		r.regEvent(new OnlineHandler());
		r.regEvent(new Garpoon());
		r.regEvent(new AntiClicker());
		r.regEvent(new Authenticator());
		r.regEvent(new PacketListener());
		r.regEvent(new Invseer());
		r.regEvent(new TotalDisabler());
		r.regEvent(new Trampoline());
		r.regEvent(new GUIHandler());
		r.regEvent(new pro.delfik.lmao.outward.inventory.GUIHandler());
	}
	
	public static void commands() {
		final LmaoCommand[] cmds = new LmaoCommand[] {new CommandAdmin(), new CommandGamemode(),
				new CommandHelp(), new CommandSudo(), new CommandTeleport(), new CommandSchem(),
				new CommandVanish(), new CommandList(), new CommandStop(), new CommandPlayer(),
				new CommandActions(), new CmdI(), new CommandMultipleCmds()};
		new Thread(() -> {
			for (LmaoCommand cmd : cmds) r.regCommand(cmd);
		}).start();
	}
}
