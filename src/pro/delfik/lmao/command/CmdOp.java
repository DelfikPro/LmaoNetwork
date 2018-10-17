package pro.delfik.lmao.command;

import implario.util.Rank;
import org.bukkit.entity.Player;
import pro.delfik.lmao.command.handle.Cmd;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.user.Person;

@Cmd(name = "op", rank = Rank.ADMIN, help = "[Игрок]", args = 1)
public class CmdOp extends LmaoCommand {
    @Override
    public void run(Person person, String args[]) {
        Player player = requirePlayer(args[0]);
        //TODO Изменить стиль сообщений
        person.sendMessage(player.isOp() ? "У игрока уже есть OP" : "Игроку был выдан OP");
        player.setOp(true);
    }
}
