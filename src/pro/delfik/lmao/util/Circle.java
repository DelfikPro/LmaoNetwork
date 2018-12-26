package pro.delfik.lmao.util;

import java.util.function.Consumer;

public class Circle implements Figure{
    private final Vec3i one, two, center;
    private final double distance;

    public Circle(Vec vec, double distance){
        this.one = new Vec3i(vec.getX() - distance, vec.getY(), vec.getZ() - distance);
        this.two = new Vec3i(vec.getX() + distance, vec.getY(), vec.getZ() + distance);
        this.center = vec.toVec3i();
        this.distance = distance * distance;
    }

    @Override
    public void foreachExecutor(Consumer<Vec3i> consumer) {
        for(int x = one.getX(); x < two.getX(); x++)
            for(int z = one.getZ(); z < two.getZ(); z++) {
                Vec3i vec = new Vec3i(x, one.getY(), z);
                if(vec.distanceIntSquared(center) < distance)
                    consumer.accept(vec);
            }
    }
}
