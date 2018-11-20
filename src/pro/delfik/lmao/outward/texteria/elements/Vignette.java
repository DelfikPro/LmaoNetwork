/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

import pro.delfik.lmao.outward.texteria.utils.Animation2D;
import pro.delfik.lmao.outward.texteria.utils.Attachment;
import pro.delfik.lmao.outward.texteria.utils.OnClick;
import pro.delfik.lmao.outward.texteria.utils.Position;

public class Vignette
extends Element<Vignette> {
    public Vignette(String id) {
        super(id);
    }

    @Override
    public Vignette setAttachment(Attachment attach) {
        throw new IllegalStateException("Vignette can not have attachment");
    }

    @Override
    public Vignette setAnimation(Animation2D anim) {
        throw new IllegalStateException("Vignette can not have animation");
    }

    @Override
    public Vignette setHoverable(boolean hoverable) {
        throw new IllegalStateException("Vignette can not have hover effect");
    }

    @Override
    public Vignette setOnClick(OnClick click) {
        throw new IllegalStateException("Vignette can not have click action");
    }

    @Override
    public Vignette setOffset(int x, int y) {
        throw new IllegalStateException("Vignette can not have offset");
    }

    @Override
    public Vignette setPosition(Position pos) {
        throw new IllegalStateException("Vignette can not have position");
    }

    @Override
    public Vignette setScale(float scale) {
        throw new IllegalStateException("Vignette can not have scale");
    }

    @Override
    public Vignette setScale(float scaleX, float scaleY) {
        throw new IllegalStateException("Vignette can not have scale");
    }

    @Override
    protected String getType() {
        return "Vignette";
    }
}

