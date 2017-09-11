package iisi.tcd.kbz.util;

import iisi.tcd.kbz.annotation.TextBean;

public class TextBeanUtil {
	public static <T> T parseBean(String text,Class<T> clazz){
		clazz.getDeclaredAnnotation(TextBean.class);
		return null; 
	}
	
	public static String parseString(Object bean) {
		return null;
	}
}
