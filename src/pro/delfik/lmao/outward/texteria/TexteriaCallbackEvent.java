/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.player.PlayerEvent
 */
package pro.delfik.lmao.outward.texteria;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import pro.delfik.lmao.outward.texteria.utils.ByteMap;

public class TexteriaCallbackEvent
extends PlayerEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ByteMap data;

    public TexteriaCallbackEvent(Player who, ByteMap data) {
        super(who);
        this.data = data;
    }

    public ByteMap getData() {
        return this.data;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

