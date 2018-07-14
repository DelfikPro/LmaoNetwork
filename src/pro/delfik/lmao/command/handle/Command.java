package pro.delfik.lmao.command.handle;

import pro.delfik.lmao.permissions.Rank;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(java.lang.annotation.ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name();
    Rank rankRequired() default Rank.PLAYER;
    int argsRequired() default 0;
    String usage() default "Использование не указано";
    String description() default "Описание отсутствует";
}