/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class ProgressBar
extends Rectangle {
    protected float progress;
    protected int barColor;
    protected int borderColor;

    public ProgressBar(String id, int width, int height, float progress) {
        super(id, width, height);
        this.progress = progress;
        this.barColor = -1;
        this.borderColor = -1;
    }

    public ProgressBar setBarColor(int color) {
        this.barColor = color;
        return this;
    }

    public ProgressBar setBorderColor(int color) {
        this.borderColor = color;
        return this;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getBarColor() {
        return this.barColor;
    }

    public int getBorderColor() {
        return this.borderColor;
    }

    public float getProgress() {
        return this.progress;
    }

    @Override
    public void write(ByteMap map) {
        super.write(map);
        map.put("barColor", this.barColor);
        if (this.progress != -99.0f) {
            map.put("progress", this.progress);
        }
        if (this.borderColor != -1) {
            map.put("border", this.borderColor);
        }
    }

    @Override
    protected String getType() {
        return "ProgressBar";
    }
}

