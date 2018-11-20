/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class TextTimer
extends Text {
    protected long millis = -1L;

    public /* varargs */ TextTimer(String id, String ... text) {
        super(id, text);
    }

    public TextTimer setTimerDuration(long millis) {
        this.millis = millis;
        return this;
    }

    public long getTimerDuration() {
        return this.millis;
    }

    @Override
    public void write(ByteMap map) {
        super.write(map);
        if (this.millis != -1L) {
            map.put("millis", this.millis);
        }
    }

    @Override
    protected String getType() {
        return "TextTimer";
    }
}

