/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package pro.delfik.lmao.outward.texteria;

import org.bukkit.entity.Player;
import pro.delfik.lmao.outward.texteria.elements.Element;
import pro.delfik.lmao.outward.texteria.utils.ByteMap;
import pro.delfik.lmao.outward.texteria.utils.Visibility;

import java.util.Collection;

public class Texteria2D {
    private static byte[] removeAllPacket;

    public static /* varargs */ void add(Element element, Player ... players) {
        Texteria.sendPacket(Texteria2D.packetAdd(element), players);
    }

    public static void add(Element element, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria2D.packetAdd(element), players);
    }

    public static byte[] packetAdd(Element element) {
        ByteMap map = new ByteMap();
        map.put("%", "add");
        element.write(map);
        return map.toByteArray();
    }

    public static /* varargs */ void add(Element[] elements, Player ... players) {
        Texteria.sendPacket(Texteria2D.packetAddBulk(elements), players);
    }

    public static void add(Element[] elements, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria2D.packetAddBulk(elements), players);
    }

    public static byte[] packetAddBulk(Element[] elements) {
        ByteMap map = new ByteMap();
        map.put("%", "add:bulk");
        ByteMap[] e = new ByteMap[elements.length];
        for (int i = 0; i < elements.length; ++i) {
            e[i] = new ByteMap();
            elements[i].write(e[i]);
        }
        map.put("e", e);
        return map.toByteArray();
    }

    public static /* varargs */ void add(Visibility vis, Element[] elements, Player ... players) {
        Texteria.sendPacket(Texteria2D.packetAddGroup(vis, elements), players);
    }

    public static void add(Visibility vis, Element[] elements, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria2D.packetAddGroup(vis, elements), players);
    }

    public static byte[] packetAddGroup(Visibility vis, Element[] elements) {
        ByteMap map = new ByteMap();
        map.put("%", "add:group");
        ByteMap[] e = new ByteMap[elements.length];
        for (int i = 0; i < elements.length; ++i) {
            elements[i].setVisibility(null);
            e[i] = new ByteMap();
            elements[i].write(e[i]);
        }
        map.put("e", e);
        if (vis != null) {
            map.put("vis", vis.getSerialized());
        }
        return map.toByteArray();
    }

    public static /* varargs */ void edit(String id, ByteMap data, Player ... players) {
        Texteria.sendPacket(Texteria2D.packetEdit(id, data), players);
    }

    public static void edit(String id, ByteMap data, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria2D.packetEdit(id, data), players);
    }

    public static byte[] packetEdit(String id, ByteMap data) {
        ByteMap map = new ByteMap();
        map.put("%", "edit");
        map.put("id", id);
        map.put("data", data);
        return map.toByteArray();
    }

    public static /* varargs */ void remove(String id, Player ... players) {
        Texteria.sendPacket(Texteria2D.packetRemove(id), players);
    }

    public static void remove(String id, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria2D.packetRemove(id), players);
    }

    public static byte[] packetRemove(String id) {
        ByteMap map = new ByteMap();
        map.put("%", "rm:id");
        map.put("id", id);
        return map.toByteArray();
    }

    public static /* varargs */ void removeGroup(String group, Player ... players) {
        Texteria.sendPacket(Texteria2D.packetRemoveGroup(group), players);
    }

    public static void removeGroup(String group, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria2D.packetRemoveGroup(group), players);
    }

    public static byte[] packetRemoveGroup(String group) {
        ByteMap map = new ByteMap();
        map.put("%", "rm:group");
        map.put("group", group);
        return map.toByteArray();
    }

    public static /* varargs */ void removeAll(Player ... players) {
        Texteria.sendPacket(removeAllPacket, players);
    }

    public static void removeAll(Collection<? extends Player> players) {
        Texteria.sendPacket(removeAllPacket, players);
    }

    static {
        ByteMap map = new ByteMap();
        map.put("%", "rm:all");
        removeAllPacket = map.toByteArray();
    }
}

