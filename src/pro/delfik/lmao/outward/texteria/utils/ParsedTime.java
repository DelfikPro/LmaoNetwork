/*
 * Decompiled with CFR 0_132.
 */
package pro.delfik.lmao.outward.texteria.utils;

public class ParsedTime {
    public final int millis;
    public final int seconds;
    public final int minutes;
    public final int hours;
    public final int days;

    public ParsedTime(long inp) {
        this.millis = (int)(inp % 1000L);
        this.seconds = (int)((inp /= 1000L) % 60L);
        this.minutes = (int)((inp /= 60L) % 60L);
        this.hours = (int)((inp /= 60L) % 24L);
        this.days = (int)(inp /= 24L);
    }

    public static String numToString(int num, int length) {
        String str = "" + num + "";
        while (str.length() < length) {
            str = "0" + str;
        }
        if (str.length() > length) {
            str = str.substring(0, length);
        }
        return str;
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        if (this.days != 0) {
            sb.append(this.days).append(" \u0434. ");
        }
        if (this.hours != 0) {
            sb.append(this.hours).append(" \u0447. ");
        }
        if (this.minutes != 0) {
            sb.append(this.minutes).append(" \u043c. ");
        }
        if (this.seconds != 0) {
            sb.append(this.seconds).append(" \u0441.");
        }
        return sb.toString().trim();
    }
}

