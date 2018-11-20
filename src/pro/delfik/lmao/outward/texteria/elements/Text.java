/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package pro.delfik.lmao.outward.texteria.elements;

import org.bukkit.ChatColor;
import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class Text
extends Element<Text> {
    public static final int LEFT = 1;
    public static final int CENTER = 2;
    public static final int RIGHT = 3;
    protected String[] text;
    protected int orientation = 2;
    protected int width = -1;
    protected boolean shadow = true;
    protected int background = -1;
    protected int hoverBackground = -1;

    public /* varargs */ Text(String id, String ... lines) {
        super(id);
        this.setText(lines);
    }

    public Text setOrientation(int orientation) {
        this.orientation = orientation;
        return this;
    }

    public Text setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    public Text setWidth(int width) {
        this.width = width;
        return this;
    }

    public /* varargs */ Text setText(String ... lines) {
        this.text = new String[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            this.text[i] = ChatColor.translateAlternateColorCodes('&', lines[i]);
        }
        return this;
    }

    public Text setBackground(int background) {
        this.background = background;
        return this;
    }

    public Text setHoverBackground(int hoverBackground) {
        this.hoverBackground = hoverBackground;
        return this;
    }

    public String[] getText() {
        return this.text;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public int getWidth() {
        return this.width;
    }

    public int getBackground() {
        return this.background;
    }

    @Override
    public void write(ByteMap map) {
        super.write(map);
        map.put("text", this.text);
        if (this.width != -1) {
            map.put("width", this.width);
        }
        if (this.orientation != 2) {
            map.put("or", this.orientation);
        }
        if (!this.shadow) {
            map.put("shadow", false);
        }
        if (this.background != -1) {
            map.put("bg", this.background);
        }
        if (this.hoverBackground != -1) {
            map.put("hoverBg", this.background);
        }
    }

    @Override
    protected String getType() {
        return "Text";
    }
}

