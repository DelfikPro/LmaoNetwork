package pro.delfik.lmao.util;

import java.util.function.Consumer;

public class Cuboid implements Figure{
    private final Vec3i one, two;

    public Cuboid(Vec one, Vec two){
        this.one = new Vec3i(Math.min(one.x(), two.x()), Math.min(one.y(), two.y()), Math.min(one.z(), two.z()));
        this.two = new Vec3i(Math.max(one.x(), two.x()), Math.max(one.y(), two.y()), Math.max(one.z(), two.z()));
    }

    @Override
    public void foreach(Consumer<Vec3i> consumer) {
        for(int x = one.getX(); x < two.getX(); x++)
            for(int y = one.getY(); x < two.getY(); y++)
                for(int z = one.getZ(); z < two.z(); z++)
                    consumer.accept(new Vec3i(x, y, z));
    }
}
