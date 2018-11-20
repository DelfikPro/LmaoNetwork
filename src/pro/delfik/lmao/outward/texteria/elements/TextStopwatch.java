/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

public class TextStopwatch
extends Text {
    public /* varargs */ TextStopwatch(String id, String ... lines) {
        super(id, lines);
    }

    @Override
    protected String getType() {
        return "TextStopwatch";
    }
}

