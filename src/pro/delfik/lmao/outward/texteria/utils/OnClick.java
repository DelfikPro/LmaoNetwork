/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.utils;

public class OnClick {
    public Action action;
    public Object data;

    public OnClick(Action action, Object data) {
        this.action = action;
        this.data = data;
    }

    public enum Action {
        URL,
        CHAT,
        CALLBACK;
        

        Action() {
        }
    }

}

