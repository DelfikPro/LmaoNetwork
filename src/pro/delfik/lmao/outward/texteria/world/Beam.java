/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.world;

import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class Beam
extends WorldGroup {
    public int color;

    public Beam(String id, int color) {
        super(id);
        this.color = color;
    }

    @Override
    public void write(ByteMap map) {
        super.write(map);
        map.put("type", "beam");
        map.put("color", this.color);
        map.remove("e");
    }
}

