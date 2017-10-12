package iisi.tcd.textMapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import iisi.tcd.textMapper.Align;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface TextMapper {
	int length() default 0;

	Align align() default Align.LEFT;

	String repeat() default "";

	char paddingWord() default ' ';
}
