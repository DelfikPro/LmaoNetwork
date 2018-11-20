/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class Button
extends Element<Button> {
    protected int width;
    protected int height;
    protected int hoverColor = -1;
    protected String text;
    protected int textColor = -1;

    public Button(String id, int width, int height, String text) {
        super(id);
        this.width = width;
        this.height = height;
        this.text = text;
        this.hoverable = true;
    }

    public Button setHoverColor(int color) {
        this.hoverColor = color;
        return this;
    }

    public Button setTextColor(int color) {
        this.textColor = color;
        return this;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getHoverColor() {
        return this.hoverColor;
    }

    public String getText() {
        return this.text;
    }

    public int getTextColor() {
        return this.textColor;
    }

    @Override
    public void write(ByteMap map) {
        super.write(map);
        map.put("w", this.width);
        map.put("h", this.height);
        map.put("t", this.text);
        if (this.hoverColor != -1) {
            map.put("hc", this.hoverColor);
        }
        if (this.textColor != -1) {
            map.put("tc", this.textColor);
        }
        if (!this.hoverable) {
            map.put("hv", false);
        } else {
            map.remove("hv");
        }
    }

    @Override
    protected String getType() {
        return "Button";
    }
}

