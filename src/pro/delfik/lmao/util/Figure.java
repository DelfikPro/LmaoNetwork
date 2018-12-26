package pro.delfik.lmao.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pro.delfik.lmao.Lmao;

import java.util.function.Consumer;

public interface Figure {
    void foreach(Consumer<Vec3i> consumer);

    default void set(Material material, byte data){
        boolean needData = data != 0;
        Bukkit.getScheduler().runTask(Lmao.plugin, () -> foreach((vec) -> {
            Block block = vec.block();
            block.setType(material);
            if(needData) block.setData(data);
        }));
    }

    default void set(Material material){
        set(material, (byte)0);
    }
}
