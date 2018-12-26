package pro.delfik.lmao.util;

import java.util.function.Consumer;

public class Square implements Figure{
    private final Vec3i one, two;

    public Square(Vec one, Vec two){
        int y = Math.min(one.getY(), two.getY());
        this.one = new Vec3i(Math.min(one.x(), two.x()), y, Math.min(one.z(), two.z()));
        this.two = new Vec3i(Math.max(one.x(), two.x()), y, Math.max(one.z(), two.z()));
    }

    @Override
    public void foreach(Consumer<Vec3i> consumer) {
        for(int x = one.getX(); x < two.getX(); x++)
           for(int z = one.getZ(); z < two.getZ(); z++)
               consumer.accept(new Vec3i(x, one.getY(), z));
    }
}
