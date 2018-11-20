/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Visibility {
    public static final Visibility DEFAULT = new Visibility().ingame(true).chat(true);
    public static final Visibility ALWAYS = new Visibility().always(true);
    public static final Visibility ALWAYS_SCREEN = new Visibility().always(true).layer(Layer.SCREEN);
    public static final Visibility ALWAYS_EXCEPT_TAB = new Visibility().always(true).tab(false);
    public static final Visibility ALWAYS_SCREEN_EXCEPT_TAB = new Visibility().always(true).tab(false).layer(Layer.SCREEN);
    public static final Visibility INGAME_EXCEPT_TAB = new Visibility().ingame(true).chat(true).tab(false);
    private final List<ByteMap> list = new ArrayList<>();
    private Layer layer = Layer.HUD;
    private ByteMap[] baked;

    public Visibility always(boolean show) {
        return this.add(show, map -> map.put("type", "always"));
    }

    public Visibility ingame(boolean show) {
        return this.add(show, map -> map.put("type", "ingame"));
    }

    public Visibility gui(boolean show, String clazz) {
        return this.add(show, map -> {
            map.put("type", "gui");
            map.put("class", clazz);
        });
    }

    public Visibility inventory(boolean show, String name) {
        return this.add(show, map -> {
            map.put("type", "inv");
            map.put("name", name);
        });
    }

    public Visibility chat(boolean show) {
        return this.add(show, map -> map.put("type", "chat"));
    }

    public Visibility f3(boolean show) {
        return this.add(show, map -> map.put("type", "f3"));
    }

    public Visibility fps(boolean show) {
        return this.add(show, map -> map.put("type", "fps"));
    }

    public Visibility tab(boolean show) {
        return this.add(show, map -> map.put("type", "tab"));
    }

    public Visibility bossbar(boolean show) {
        return this.add(show, map -> map.put("type", "bossbar"));
    }

    public Visibility layer(Layer layer) {
        this.layer = layer;
        this.baked = null;
        return this;
    }

    private Visibility add(boolean show, Consumer<ByteMap> writer) {
        ByteMap map = new ByteMap();
        map.put("show", show);
        writer.accept(map);
        this.list.add(map);
        this.baked = null;
        return this;
    }

    public ByteMap[] getSerialized() {
        if (this.baked == null) {
            if (this.layer == Layer.HUD) {
                this.baked = this.list.toArray(new ByteMap[0]);
            } else {
                ByteMap[] arr = new ByteMap[this.list.size() + 1];
                this.list.toArray(arr);
                arr[arr.length - 1] = this.layer.serialized;
                this.baked = arr;
            }
        }
        return this.baked;
    }

    public enum Layer {
        HUD,
        SCREEN;
        
        private final ByteMap serialized = new ByteMap();

        Layer() {
            this.serialized.put("type", "layer");
            this.serialized.put("layer", this.name());
        }
    }

}

