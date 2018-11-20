/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class ProgressTimer
extends ProgressBar {
    protected boolean reverse = false;

    public ProgressTimer(String id, int width, int height) {
        super(id, width, height, -99.0f);
    }

    public ProgressTimer setReverse(boolean flag) {
        this.reverse = flag;
        return this;
    }

    public boolean isReverse() {
        return this.reverse;
    }

    @Override
    public void write(ByteMap map) {
        super.write(map);
        if (this.reverse) {
            map.put("reverse", true);
        }
    }

    @Override
    protected String getType() {
        return "ProgressTimer";
    }
}

