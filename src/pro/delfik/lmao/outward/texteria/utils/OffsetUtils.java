/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.utils;

import pro.delfik.lmao.outward.texteria.elements.Element;

public class OffsetUtils {
    public static final int SLOT_SIZE = 16;

    public static <T extends Element> T doubleChest(T elem, int slot) {
        if (slot < 0 || slot > 53) {
            throw new IllegalArgumentException("Slot must be between 0 and 53");
        }
        elem.setPosition(Position.CENTER).setOffset(slot % 9 * 18 - 72, slot / 9 * 18 - 85);
        return elem;
    }

    public static <T extends Element> T chest(T elem, int slot) {
        if (slot < 0 || slot >= 26) {
            throw new IllegalArgumentException("Slot must be between 0 and 26");
        }
        elem.setPosition(Position.CENTER).setOffset(slot % 9 * 18 - 72, slot / 9 * 18 - 58);
        return elem;
    }

    public static <T extends Element> T inv9(T elem, int slot) {
        if (slot < 0 || slot > 8) {
            throw new IllegalArgumentException("Slot must be between 0 and 8");
        }
        elem.setPosition(Position.CENTER).setOffset(slot * 18 - 72, -40);
        return elem;
    }

    public static <T extends Element> T inv45(T elem, int slot) {
        if (slot < 0 || slot > 44) {
            throw new IllegalArgumentException("Slot must be between 0 and 44");
        }
        elem.setPosition(Position.CENTER).setOffset(slot % 9 * 18 - 72, slot / 9 * 18 - 76);
        return elem;
    }

    public static <T extends Element> T hotbar(T elem, int slot) {
        if (slot < 0 || slot > 8) {
            throw new IllegalArgumentException("Slot must be between 0 and 8");
        }
        elem.setPosition(Position.BOTTOM).setOffset(slot * 20 - 80, 3);
        return elem;
    }
}

