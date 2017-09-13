package iisi.tcd.kbz.util;

import java.lang.reflect.Field;

import java.util.Arrays;

import java.util.stream.Stream;

import iisi.tcd.kbz.annotation.TextBean;
import iisi.tcd.kbz.annotation.TextMapper;

public class TextBeanUtil {
	public static <T> T parseBean(String text, Class<T> clazz) {
		try {
			if (null == clazz.getDeclaredAnnotation(TextBean.class)) {
				throw new RuntimeException();
			}

			T Bean = clazz.newInstance();

			Stream<Field> stream = Arrays.stream(clazz.getDeclaredFields());
			stream.forEach(field -> {
				field.setAccessible(true);
			});
			stream.filter(field -> null == field.getAnnotation(TextMapper.class));
			
		

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String parseString(Object bean) {
		return null;
	}

}
