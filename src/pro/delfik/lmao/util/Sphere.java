package pro.delfik.lmao.util;

import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class Sphere implements Figure{
    private final Vec3i one, two, center;
    private final double distance;

    public Sphere(Vec vec, double distance){
        this.one = new Vec3i(vec.getX() - distance, vec.getY() - distance, vec.getZ() - distance);
        this.two = new Vec3i(vec.getX() + distance, vec.getY() + distance, vec.getZ() + distance);
        this.center = vec.toVec3i();
        this.distance = distance * distance;
    }

    @Override
    public void foreachExecutor(Consumer<Vec3i> consumer) {
        for(int x = one.getX(); x < two.getX(); x++)
            for(int y = one.getY(); y < two.getY(); y++)
                for(int z = one.getZ(); z < two.getZ(); z++) {
                    Vec3i vec = new Vec3i(x, y, z);
                    Bukkit.broadcastMessage(x + " " + y + " " + z + " " + (vec.distanceIntSquared(center) < distance));
                    if(vec.distanceIntSquared(center) < distance)
                        consumer.accept(vec);
                }
    }
}
