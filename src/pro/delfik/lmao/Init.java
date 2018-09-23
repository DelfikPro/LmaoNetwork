package pro.delfik.lmao;

import pro.delfik.lmao.ev.EvInteract;
import pro.delfik.lmao.ev.EvChat;
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
import pro.delfik.lmao.ev.EvPacket;
import pro.delfik.lmao.ev.added.PacketEvent;
import pro.delfik.lmao.ev.EvCmdPreprocess;
import pro.delfik.lmao.ev.EvJoin;
import pro.delfik.lmao.ev.EvLeave;
import pro.delfik.lmao.ev.EvPreLogin;
import pro.delfik.lmao.misc.Garpoon;
import pro.delfik.lmao.ev.EvInteractAtEntity;
import pro.delfik.lmao.command.CommandMultipleCmds;
import pro.delfik.lmao.outward.TotalDisabler;
import pro.delfik.lmao.outward.Trampoline;
import pro.delfik.lmao.outward.gui.GUIHandler;
import pro.delfik.lmao.permissions.Authenticator;
import pro.delfik.lmao.util.Registrar;

public class Init {
}
