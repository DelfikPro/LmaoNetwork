package pro.delfik.lmao;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pro.delfik.lmao.command.CmdI;
import pro.delfik.lmao.command.CommandActions;
import pro.delfik.lmao.command.CommandAdmin;
import pro.delfik.lmao.command.CommandGamemode;
import pro.delfik.lmao.command.CommandHelp;
import pro.delfik.lmao.command.CommandList;
import pro.delfik.lmao.command.CommandMultipleCmds;
import pro.delfik.lmao.command.CommandPlayer;
import pro.delfik.lmao.command.CommandSchem;
import pro.delfik.lmao.command.CommandStop;
import pro.delfik.lmao.command.CommandSudo;
import pro.delfik.lmao.command.CommandTeleport;
import pro.delfik.lmao.command.CommandVanish;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.ev.EvChat;
import pro.delfik.lmao.ev.EvCmdPreprocess;
import pro.delfik.lmao.ev.EvInteract;
import pro.delfik.lmao.ev.EvInteractAtEntity;
import pro.delfik.lmao.ev.EvJoin;
import pro.delfik.lmao.ev.EvLeave;
import pro.delfik.lmao.ev.EvPacket;
import pro.delfik.lmao.ev.EvPreLogin;
import pro.delfik.lmao.ev.added.PacketEvent;
import pro.delfik.lmao.misc.Garpoon;
import pro.delfik.lmao.outward.TotalDisabler;
import pro.delfik.lmao.outward.Trampoline;
import pro.delfik.lmao.outward.gui.GUIHandler;
import pro.delfik.lmao.outward.gui.GUILoading;
import pro.delfik.lmao.permissions.Authenticator;
import pro.delfik.lmao.permissions.Core;
import implario.net.Packet;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.Registrar;

public class Lmao extends JavaPlugin {
	public static Lmao plugin;
	private static final String prefix = "LMAO §e> §c";
	public static boolean noPhysics = true;

	public static String p() {
		return prefix;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		Packet.init();
		Core.init();
		r = new Registrar(this);
		events();
		commands();
		GUILoading.startLoop();
		if (Bukkit.getServer().getMotd().startsWith("B_")) Person.disablePlayerCleanUp();
	}
	
	@Override
	public void onDisable() {
		Bukkit.broadcastMessage("§c§lСервер перезагружается. Все игры прерваны.");
		Core.disable();
	}
	
	public static String getColoredName(CommandSender sender) {
		return sender instanceof Player ? ((Player) sender).getDisplayName() : "§7Консоль сервера";
	}

	public static Registrar r;

	public static void events() {
		PacketEvent.class.getCanonicalName();
		r.regEvent(new EvChat());
		r.regEvent(new EvPreLogin());
		r.regEvent(new Garpoon());
		r.regEvent(new EvInteract());
		r.regEvent(new Authenticator());
		r.regEvent(new EvPacket());
		r.regEvent(new EvInteractAtEntity());
		r.regEvent(new TotalDisabler());
		r.regEvent(new Trampoline());
		r.regEvent(new GUIHandler());
		r.regEvent(new EvCmdPreprocess());
		r.regEvent(new EvLeave());
		r.regEvent(new EvJoin());
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
