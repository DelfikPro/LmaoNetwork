package pro.delfik.lmao.util;

import java.util.function.Consumer;

public class Circle implements Figure{
    private final Vec3i one, two, center;
    private final double distance;

    public Circle(Vec vec, double distance){
        this.one = new Vec3i(vec.getX() - distance, vec.getY(), vec.getZ() - distance);
        this.two = new Vec3i(vec.getX() + distance, vec.getY(), vec.getZ() + distance);
        this.center = vec.toVec3i();
        this.distance = distance;
    }

    @Override
    public void foreach(Consumer<Vec3i> consumer) {
        for(int x = one.getX(); x < two.getX(); x++)
            for(int z = one.getZ(); z < two.getZ(); z++) {
                Vec vec = new Vec3i(x, one.getY(), z);
                if(vec.distance(center) < distance)
                    consumer.accept(new Vec3i(x, one.getY(), z));
            }
    }
}
