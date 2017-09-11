package iisi.tcd.kbz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import iisi.tcd.kbz.Align;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface TextMapper {
	int length();

	Align align() default Align.RIGHT;

	String repeat() default "";

	char paddingWord() default ' ';
}
