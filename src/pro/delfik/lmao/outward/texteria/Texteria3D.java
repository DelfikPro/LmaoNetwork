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
import pro.delfik.lmao.outward.texteria.world.WorldGroup;

import java.util.Collection;

public class Texteria3D {
    private static byte[] removeAllPacket;

    public static /* varargs */ void addGroup(WorldGroup group, Player ... players) {
        Texteria.sendPacket(Texteria3D.packetAddGroup(group), players);
    }

    public static void addGroup(WorldGroup group, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria3D.packetAddGroup(group), players);
    }

    public static byte[] packetAddGroup(WorldGroup group) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:add");
        group.write(map);
        return map.toByteArray();
    }

    public static /* varargs */ void addToGroup(String group, Element element, Player ... players) {
        Texteria.sendPacket(Texteria3D.packetAddToGroup(group, element), players);
    }

    public static void addToGroup(String group, Element element, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria3D.packetAddToGroup(group, element), players);
    }

    public static byte[] packetAddToGroup(String group, Element element) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:add:to");
        map.put("group", group);
        ByteMap els = new ByteMap();
        element.write(els);
        map.put("e", els);
        return map.toByteArray();
    }

    public static /* varargs */ void editGroup(String group, ByteMap data, Player ... players) {
        Texteria.sendPacket(Texteria3D.packetEditGroup(group, data), players);
    }

    public static void editGroup(String group, ByteMap data, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria3D.packetEditGroup(group, data), players);
    }

    public static byte[] packetEditGroup(String group, ByteMap data) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:edit");
        map.put("group", group);
        map.put("data", data);
        return map.toByteArray();
    }

    public static /* varargs */ void editElementInGroup(String group, String elem, ByteMap data, Player ... players) {
        Texteria.sendPacket(Texteria3D.packetEditElementInGroup(group, elem, data), players);
    }

    public static void editElementInGroup(String group, String elem, ByteMap data, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria3D.packetEditElementInGroup(group, elem, data), players);
    }

    public static byte[] packetEditElementInGroup(String group, String elem, ByteMap data) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:edit:in");
        map.put("group", group);
        map.put("id", elem);
        map.put("data", data);
        return map.toByteArray();
    }

    public static /* varargs */ void removeGroup(String group, Player ... players) {
        Texteria.sendPacket(Texteria3D.packetRemoveGroup(group), players);
    }

    public static void removeGroup(String group, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria3D.packetRemoveGroup(group), players);
    }

    public static byte[] packetRemoveGroup(String group) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:rm");
        map.put("group", group);
        return map.toByteArray();
    }

    public static /* varargs */ void removeFromGroup(String group, String element, Player ... players) {
        Texteria.sendPacket(Texteria3D.packetRemoveFromGroup(group, element), players);
    }

    public static void removeFromGroup(String group, String element, Collection<? extends Player> players) {
        Texteria.sendPacket(Texteria3D.packetRemoveFromGroup(group, element), players);
    }

    public static byte[] packetRemoveFromGroup(String group, String element) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:rm:from");
        map.put("group", group);
        map.put("id", element);
        return map.toByteArray();
    }

    public static /* varargs */ void removeAllGroups(Player ... players) {
        Texteria.sendPacket(removeAllPacket, players);
    }

    public static void removeAllGroups(Collection<? extends Player> players) {
        Texteria.sendPacket(removeAllPacket, players);
    }

    static {
        ByteMap map = new ByteMap();
        map.put("%", "3d:rm:all");
        removeAllPacket = map.toByteArray();
    }
}

