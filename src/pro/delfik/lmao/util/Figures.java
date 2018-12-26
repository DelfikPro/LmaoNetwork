package pro.delfik.lmao.util;

import java.util.function.Consumer;

public class Figures implements Figure{
    private final Figure figures[];

    public Figures(Figure... figures){
        this.figures = figures;
    }

    @Override
    public void foreachExecutor(Consumer<Vec3i> consumer) {
        for(Figure figure : figures)
            figure.foreach(consumer);
    }
}
