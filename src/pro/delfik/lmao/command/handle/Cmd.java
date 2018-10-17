package pro.delfik.lmao.command.handle;

import implario.util.Rank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cmd {
    String name();

    int args() default 0;

    String help() default "";

    Rank rank() default Rank.PLAYER;

    String description() default "";
}
