package iisi.tcd.kbz.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;

import iisi.tcd.kbz.annotation.TextBean;
import iisi.tcd.kbz.annotation.TextMapper;

public class TextBeanUtil {
	public static <T> T parseBean(String text, Class<T> clazz) {

		try {
			if (null == clazz.getDeclaredAnnotation(TextBean.class)) {
				throw new RuntimeException();
			}
			T bean = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();

			int current = 0;
			for (Field field : fields) {
				field.setAccessible(true);
				TextMapper textMapper = field.getDeclaredAnnotation(TextMapper.class);

				Type type = field.getGenericType();
				if (textMapper == null) {
					continue;
				} else if (type.equals(String.class)) {
					field.set(bean, text.substring(current, current + textMapper.length()));
					current += textMapper.length();
				} else if (type.equals(Integer.class) || type.equals(int.class)) {
					field.set(bean, Integer.valueOf(text.substring(current, current + textMapper.length())));
					current += textMapper.length();
				} else if (type instanceof ParameterizedType) {
					List list = new ArrayList();
					field.set(bean, list);
					Class detailClazz = (Class) Class
							.forName(((ParameterizedType) type).getActualTypeArguments()[0].getTypeName());
					int repeat = getRepeatNumber(field, bean);
					for (int i = 0; i < repeat; i++) {
						list.add(parseBean(text.substring(current, current + textMapper.length()), detailClazz));
						current += textMapper.length();
					}
				}

			}
			return bean;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String parseString(Object bean) {
		return null;
	}

	private static int getRepeatNumber(Field field, Object bean) throws ClassNotFoundException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Class clazz = bean.getClass();
		TextMapper textMapper = field.getDeclaredAnnotation(TextMapper.class);
		String repeat = textMapper.repeat();
		try {
			int count = Integer.parseInt(repeat);
			return count > 0 ? count : 0;
		} catch (NumberFormatException e) {
			Field referenceField = clazz.getDeclaredField(repeat);
			if (referenceField.getGenericType().equals(int.class)
					|| referenceField.getGenericType().equals(Integer.class)) {
				referenceField.setAccessible(true);
				return (int) referenceField.get(bean);
			} else {
				return 0;
			}
		}
	}

}
