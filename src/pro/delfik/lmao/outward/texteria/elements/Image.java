/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class Image
extends Rectangle {
    protected String image;
    protected byte[] data = null;

    public Image(String id, int size, String image) {
        this(id, size, size, image);
    }

    public Image(String id, int width, int height, String image) {
        super(id, width, height);
        this.image = image;
    }

    public Image setData(byte[] data) {
        this.data = data;
        return this;
    }

    public String getImage() {
        return this.image;
    }

    public byte[] getData() {
        return this.data;
    }

    @Override
    public void write(ByteMap map) {
        super.write(map);
        map.put("image", this.image);
        if (this.data != null) {
            map.put("idata", this.data);
        }
    }

    @Override
    protected String getType() {
        return "Image";
    }
}

