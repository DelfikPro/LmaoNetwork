/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.elements;

import pro.delfik.lmao.outward.texteria.utils.*;

public abstract class Element<T extends Element> {
    protected final String id;
    protected int color = -1;
    protected long duration = -1L;
    protected Position pos = Position.CENTER;
    protected Attachment attach = null;
    protected Visibility visibility = null;
    protected Animation2D anim = null;
    protected float scaleX = 1.0f;
    protected float scaleY = 1.0f;
    protected int x = 0;
    protected int y = 0;
    protected float rotation = 0.0f;
    protected int delay = 0;
    protected int fadeStart = 255;
    protected int fadeFinish = 255;
    protected OnClick click;
    protected boolean hoverable = false;

    protected Element(String id) {
        this.id = id;
    }

    public T setScale(float scale) {
        return this.setScale(scale, scale);
    }

    public T setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        return (T)this;
    }

    public T setColor(int color) {
        this.color = color;
        return (T)this;
    }

    public T setPosition(Position pos) {
        this.pos = pos;
        return (T)this;
    }

    public T setOffset(int x, int y) {
        this.x = x;
        this.y = y;
        return (T)this;
    }

    public T setDuration(long duration) {
        this.duration = duration;
        return (T)this;
    }

    public T setDelay(int delay) {
        this.delay = delay;
        return (T)this;
    }

    public T setFadeStart(int fade) {
        this.fadeStart = fade;
        return (T)this;
    }

    public T setFadeFinish(int fade) {
        this.fadeFinish = fade;
        return (T)this;
    }

    public T setFade(int fade) {
        this.fadeFinish = this.fadeStart = fade;
        return (T)this;
    }

    public T setRotation(float angle) {
        this.rotation = angle;
        return (T)this;
    }

    public T setHoverable(boolean hoverable) {
        this.hoverable = hoverable;
        return (T)this;
    }

    public T setOnClick(OnClick click) {
        this.hoverable = true;
        this.click = click;
        return (T)this;
    }

    public T setVisibility(Visibility visibility) {
        this.visibility = visibility;
        return (T)this;
    }

    public T setAttachment(Attachment attach) {
        this.attach = attach;
        return (T)this;
    }

    public T setAnimation(Animation2D anim) {
        this.anim = anim;
        return (T)this;
    }

    public String getId() {
        return this.id;
    }

    public int getOffsetX() {
        return this.x;
    }

    public int getOffsetY() {
        return this.y;
    }

    public Position getPosition() {
        return this.pos;
    }

    public int getColor() {
        return this.color;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public Animation2D getAnimation() {
        return this.anim;
    }

    public Attachment getAttachment() {
        return this.attach;
    }

    public float getRotation() {
        return this.rotation;
    }

    public int getDelay() {
        return this.delay;
    }

    public int getFadeStart() {
        return this.fadeStart;
    }

    public int getFadeFinish() {
        return this.fadeFinish;
    }

    public long getDuration() {
        return this.duration;
    }

    public OnClick getOnClick() {
        return this.click;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public boolean isHoverable() {
        return this.hoverable;
    }

    public void write(ByteMap map) {
        map.put("type", this.getType());
        if (this.id != null) {
            map.put("id", this.id);
        }
        if (this.x != 0) {
            map.put("x", this.x);
        }
        if (this.y != 0) {
            map.put("y", this.y);
        }
        if (this.duration > 0L) {
            map.put("dur", this.duration);
        }
        if (this.delay != 0) {
            map.put("delay", this.delay);
        }
        if (this.color != -1) {
            map.put("color", this.color);
        }
        if (this.rotation != 0.0f) {
            map.put("rot", this.rotation);
        }
        if (this.hoverable && this.click == null) {
            map.put("hv", true);
        }
        if (this.scaleX != 1.0f || this.scaleY != 1.0f) {
            if (this.scaleX == this.scaleY) {
                map.put("scale", this.scaleX);
            } else {
                if (this.scaleX != 1.0f) {
                    map.put("scale.x", this.scaleX);
                }
                if (this.scaleY != 1.0f) {
                    map.put("scale.y", this.scaleY);
                }
            }
        }
        if (this.fadeStart != 255 || this.fadeFinish != 255) {
            if (this.fadeStart == this.fadeFinish) {
                map.put("fade", this.fadeStart);
            } else {
                if (this.fadeStart != 255) {
                    map.put("fade.s", this.fadeStart);
                }
                if (this.fadeFinish != 255) {
                    map.put("fade.f", this.fadeFinish);
                }
            }
        }
        if (this.click != null) {
            ByteMap c = new ByteMap();
            c.put("act", this.click.action.name());
            c.put("data", this.click.data);
            map.put("click", c);
        }
        if (this.visibility != null) {
            map.put("vis", this.visibility.getSerialized());
        }
        if (this.attach != null) {
            map.put("attach.to", this.attach.attachTo);
            map.put("attach.loc", this.attach.attachLocation.name());
            if (this.attach.attachLocation != this.attach.orientation) {
                map.put("attach.orient", this.attach.orientation.name());
            }
            if (!this.attach.removeWhenParentRemove) {
                map.put("attach.rwpr", false);
            }
        } else if (this.pos != Position.CENTER) {
            map.put("pos", this.pos.name());
        }
        if (this.anim != null) {
            if (this.anim.start != null) {
                map.put("anim.s", this.anim.start.serialize());
            }
            if (this.anim.finish != null) {
                map.put("anim.f", this.anim.finish.serialize());
            }
        }
    }

    protected abstract String getType();
}

