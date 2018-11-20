/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package pro.delfik.lmao.outward.texteria.utils;

import org.bukkit.entity.Player;
import pro.delfik.lmao.outward.texteria.Texteria2D;
import pro.delfik.lmao.outward.texteria.elements.Element;

import java.util.LinkedList;
import java.util.List;

public class TexteriaBuffer {
    private List<Element> list = new LinkedList<>();
    private boolean enabled = false;

    public void enable() {
        this.enabled = true;
    }

    public /* varargs */ void add(Element com, Player ... players) {
        if (this.enabled) {
            this.list.add(com);
        } else {
            Texteria2D.add(com, players);
        }
    }

    public /* varargs */ void send(Player ... players) {
        this.send(null, players);
    }

    public /* varargs */ void send(Visibility vis, Player ... players) {
        this.enabled = false;
        if (this.list.isEmpty()) {
            return;
        }
        if (this.list.size() == 1) {
            Texteria2D.add(this.list.get(0).setVisibility(vis), players);
        } else {
            Texteria2D.add(vis, this.list.toArray(new Element[this.list.size()]), players);
        }
        this.list.clear();
    }
}

