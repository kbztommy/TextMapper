package iisi.tcd.textMapper.util;

public interface TextBeanStrategy {
	<T> T parseBean(String text, Class<T> clazz);

	String parseString(Object bean);
}
