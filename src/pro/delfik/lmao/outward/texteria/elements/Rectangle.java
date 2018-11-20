/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class Rectangle
extends Element<Rectangle> {
    protected int width;
    protected int height;

    public Rectangle(String id, int size) {
        this(id, size, size);
    }

    public Rectangle(String id, int width, int height) {
        super(id);
        this.width = width;
        this.height = height;
    }

    public Rectangle setSize(int size) {
        this.width = size;
        this.height = size;
        return this;
    }

    public Rectangle setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public void write(ByteMap map) {
        super.write(map);
        if (this.width == this.height) {
            map.put("size", this.width);
        } else {
            map.put("width", this.width);
            map.put("height", this.height);
        }
    }

    @Override
    protected String getType() {
        return "Rectangle";
    }
}

