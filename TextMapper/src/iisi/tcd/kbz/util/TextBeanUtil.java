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
			Field[] fields = clazz.getDeclaredFields();		
			
			Stream<Field> stream = Arrays.stream(fields);		
			
			stream.filter(field -> null != field.getDeclaredAnnotation(TextMapper.class)).forEach(field -> {
				field.setAccessible(true);
				System.out.println(field.getName());
			});
			
			
		

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
