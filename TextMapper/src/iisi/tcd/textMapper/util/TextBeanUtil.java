package iisi.tcd.textMapper.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import iisi.tcd.textMapper.Align;
import iisi.tcd.textMapper.annotation.TextBean;
import iisi.tcd.textMapper.annotation.TextMapper;

public class TextBeanUtil {
	public static <T> T parseBean(String text, Class<T> clazz) {

		try {
			if (null == clazz.getDeclaredAnnotation(TextBean.class)) {
				throw new RuntimeException("11");
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
				} else {
					throw new RuntimeException("22");
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
		Class clazz = bean.getClass();
		if (null == clazz.getDeclaredAnnotation(TextBean.class)) {
			throw new RuntimeException("11");
		}
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		for (Field f : fields) {
			try {
				f.setAccessible(true);
				TextMapper annotation = f.getDeclaredAnnotation(TextMapper.class);
				if (annotation == null) {
					continue;
				}
				sb.append(getFieldText(f, bean));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(f.getName() + "解析錯誤");
			}
		}
		return sb.toString();
	}

	private static String getFieldText(Field f, Object bean) throws Exception {
		Type type = f.getGenericType();
		TextMapper annotation = f.getDeclaredAnnotation(TextMapper.class);

		if (type.equals(String.class) || type.equals(Integer.class) || type.equals(int.class)) {
			return format(f, bean);
		} else if (type instanceof ParameterizedType) {
			StringBuilder sb = new StringBuilder();
			int repeatNumber = getRepeatNumber(f, bean);
			Collection details = (Collection) f.get(bean);
			if (details == null) {
				details = new ArrayList();
			}
			details.forEach(detail -> sb.append(parseString(detail)));

			int emptyCount = repeatNumber - details.size();
			if (emptyCount > 0) {
				Class detailClazz = (Class) Class
						.forName(((ParameterizedType) type).getActualTypeArguments()[0].getTypeName());

				Object empty = detailClazz.newInstance();
				for (int i = 0; i < emptyCount; i++) {
					sb.append(parseString(empty));
				}
			}
			return sb.toString();
		}
		throw new RuntimeException(f.getName() + "解析錯誤");
	}

	private static String format(Field f, Object o) throws Exception {
		String reuslt = null;
		String value = f.get(o) == null ? "" : String.valueOf(f.get(o));
		TextMapper annotation = f.getDeclaredAnnotation(TextMapper.class);
		if (annotation.align() == Align.LEFT) {
			reuslt = StringUtils.leftPad(value, annotation.length(), annotation.paddingWord());
		} else {
			reuslt = StringUtils.rightPad(value, annotation.length(), annotation.paddingWord());
		}
		return reuslt;
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
