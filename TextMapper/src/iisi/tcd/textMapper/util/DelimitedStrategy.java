package iisi.tcd.textMapper.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import iisi.tcd.textMapper.annotation.DelimitedTextBean;
import iisi.tcd.textMapper.annotation.FixedLengthTextBean;
import iisi.tcd.textMapper.annotation.TextMapper;
import iisi.tcd.textMapper.exception.TextBeanError;
import iisi.tcd.textMapper.exception.TextMapperException;

public class DelimitedStrategy implements TextBeanStrategy {

	@Override
	public <T> T parseBean(String text, Class<T> clazz) {
		try {

			DelimitedTextBean delimitedTextBean = clazz.getDeclaredAnnotation(DelimitedTextBean.class);
			if (null == delimitedTextBean) {
				throw new TextMapperException(TextBeanError.NotTextBeanError);
			}
			String delimiter = delimitedTextBean.value();

			String[] strs = text.split(delimiter);

			T bean = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();

			int current = 0;
			for (Field field : fields) {
				field.setAccessible(true);

				TextMapper textMapper = field.getDeclaredAnnotation(TextMapper.class);
				if (textMapper == null) {
					continue;
				}

				Type type = field.getGenericType();
				if (type.equals(String.class)) {
					field.set(bean, strs[current]);
				} else if (type.equals(Integer.class) || type.equals(int.class)) {
					field.set(bean, Integer.valueOf(strs[current]));
				} else {
					throw new TextMapperException(TextBeanError.TypeNotSupport);
				}
				current++;
			}
			return bean;
		} catch (TextMapperException e) {
			throw e;
		} catch (Exception e) {
			throw new TextMapperException(TextBeanError.ParseError);
		}
	}

	@Override
	public String parseString(Object bean) {
		Class<? extends Object> clazz = bean.getClass();
		DelimitedTextBean delimitedTextBean = clazz.getDeclaredAnnotation(DelimitedTextBean.class);
		if (null == delimitedTextBean) {
			throw new TextMapperException(TextBeanError.NotTextBeanError);
		}
		String delimiter = delimitedTextBean.value();

		Field[] fields = clazz.getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				TextMapper annotation = field.getDeclaredAnnotation(TextMapper.class);
				if (annotation == null) {
					continue;
				}
				Type type = field.getGenericType();
				if (type.equals(String.class) || type.equals(Integer.class) || type.equals(int.class)) {
					String value = field.get(bean) == null ? "" : field.get(bean).toString();
					sb.append(value).append(delimiter);
				} else {
					throw new TextMapperException(TextBeanError.TypeNotSupport);
				}
			} catch (TextMapperException e) {
				throw e;
			} catch (Exception e) {
				throw new TextMapperException(TextBeanError.ParseError);
			}
		}
		return sb.substring(0, sb.length() - 1);
	}

}
