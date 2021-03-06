package iisi.tcd.textMapper.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import iisi.tcd.textMapper.Align;
import iisi.tcd.textMapper.annotation.FixedLengthTextBean;
import iisi.tcd.textMapper.annotation.TextMapper;
import iisi.tcd.textMapper.exception.TextBeanError;
import iisi.tcd.textMapper.exception.TextMapperException;

class FixedLengthStrategy implements TextBeanStrategy {

	public <T> T parseBean(String text, Class<T> clazz) {

		try {
			if (null == clazz.getDeclaredAnnotation(FixedLengthTextBean.class)) {
				throw new TextMapperException(TextBeanError.NotTextBeanError);
			}
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
					throw new TextMapperException(TextBeanError.TypeNotSupport);
				}
			}
			return bean;
		} catch (TextMapperException e) {
			throw e;
		} catch (Exception e) {
			throw new TextMapperException(TextBeanError.ParseError);
		}
	}

	public String parseString(Object bean) {
		Class clazz = bean.getClass();
		if (null == clazz.getDeclaredAnnotation(FixedLengthTextBean.class)) {
			throw new TextMapperException(TextBeanError.NotTextBeanError);
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
			} catch (TextMapperException e) {
				throw e;
			} catch (Exception e) {
				throw new TextMapperException(TextBeanError.ParseError);
			}
		}
		return sb.toString();
	}

	private String getFieldText(Field f, Object bean) throws Exception {
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
		throw new TextMapperException(TextBeanError.TypeNotSupport);
	}

	private String format(Field f, Object o) throws Exception {
		String reuslt = null;
		String value = f.get(o) == null ? "" : String.valueOf(f.get(o));
		TextMapper annotation = f.getDeclaredAnnotation(TextMapper.class);
		if (value.length() > annotation.length()) {
			throw new TextMapperException(TextBeanError.OverLength, f);
		}
		if (annotation.align() == Align.LEFT) {
			reuslt = StringUtils.leftPad(value, annotation.length(), annotation.paddingWord());
		} else {
			reuslt = StringUtils.rightPad(value, annotation.length(), annotation.paddingWord());
		}
		return reuslt;
	}

	private int getRepeatNumber(Field field, Object bean) throws ClassNotFoundException, NoSuchFieldException,
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
