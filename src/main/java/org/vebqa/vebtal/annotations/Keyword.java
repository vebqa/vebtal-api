package org.vebqa.vebtal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface Keyword {

    public String command() default "";
    public String description() default "";
    public String hintTarget() default "";
    public String hintValue() default "";

}
