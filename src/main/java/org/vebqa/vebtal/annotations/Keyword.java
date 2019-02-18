package org.vebqa.vebtal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface Keyword {

	String module() default "";
    String command() default "";
    String description() default "";
    String hintTarget() default "";
    String hintValue() default "";

}
