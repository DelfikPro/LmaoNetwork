/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

public class RadialProgressTimer
extends RadialProgressBar {
    public RadialProgressTimer(String id, int size) {
        super(id, size, -99.0f);
    }

    @Override
    protected String getType() {
        return "RadialProgressTimer";
    }
}

