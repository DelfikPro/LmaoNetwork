/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class RadialProgressBar
extends Element<RadialProgressBar> {
    protected int size;
    protected float progress;

    public RadialProgressBar(String id, int size, float progress) {
        super(id);
        this.size = size;
        this.progress = progress;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getSize() {
        return this.size;
    }

    public float getProgress() {
        return this.progress;
    }

    @Override
    public void write(ByteMap map) {
        super.write(map);
        map.put("size", this.size);
        if (this.progress != -99.0f) {
            map.put("progress", this.progress);
        }
    }

    @Override
    protected String getType() {
        return "RadialProgressBar";
    }
}

