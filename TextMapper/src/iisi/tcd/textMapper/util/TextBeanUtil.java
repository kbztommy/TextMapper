package iisi.tcd.textMapper.util;

import iisi.tcd.textMapper.annotation.DelimitedTextBean;
import iisi.tcd.textMapper.annotation.FixedLengthTextBean;
import iisi.tcd.textMapper.exception.TextBeanError;
import iisi.tcd.textMapper.exception.TextMapperException;

public class TextBeanUtil {

	/**
	 * 將文本轉換成物件。
	 * 
	 * @param text
	 *            要轉換成物件的文本
	 * @param clazz
	 *            欲轉換的class
	 * @return 符合格式則回傳物件，否則會拋出TextMapperException;
	 */
	public static <T> T parseBean(String text, Class<T> clazz) {

		try {
			TextBeanStrategy textBeanStrategy = null;
			if (null == clazz.getDeclaredAnnotation(FixedLengthTextBean.class)
					&& null == clazz.getDeclaredAnnotation(DelimitedTextBean.class)) {
				throw new TextMapperException(TextBeanError.NotTextBeanError);
			} else if (clazz.getDeclaredAnnotation(FixedLengthTextBean.class) instanceof FixedLengthTextBean) {
				textBeanStrategy = new FixedLengthStrategy();
			} else if (clazz.getDeclaredAnnotation(DelimitedTextBean.class) instanceof DelimitedTextBean) {
				textBeanStrategy = new DelimitedStrategy();
			}
			return textBeanStrategy.parseBean(text, clazz);
		} catch (TextMapperException e) {
			throw e;
		} catch (Exception e) {
			throw new TextMapperException(TextBeanError.ParseError);
		}
	}

	/**
	 * 將物件轉換成文本
	 * 
	 * @param bean
	 *            欲轉換成文本的物件
	 * @return 符合格式則回傳文本，否則會拋出TextMapperException;
	 */
	public static String parseString(Object bean) {
		Class<? extends Object> clazz = bean.getClass();
		TextBeanStrategy textBeanStrategy = null;
		if (null == clazz.getDeclaredAnnotation(FixedLengthTextBean.class)
				&& null == clazz.getDeclaredAnnotation(DelimitedTextBean.class)) {
			throw new TextMapperException(TextBeanError.NotTextBeanError);
		} else if (clazz.getDeclaredAnnotation(FixedLengthTextBean.class) instanceof FixedLengthTextBean) {
			textBeanStrategy = new FixedLengthStrategy();
		} else if (clazz.getDeclaredAnnotation(DelimitedTextBean.class) instanceof DelimitedTextBean) {
			textBeanStrategy = new DelimitedStrategy();
		}

		return textBeanStrategy.parseString(bean);
	}

}
