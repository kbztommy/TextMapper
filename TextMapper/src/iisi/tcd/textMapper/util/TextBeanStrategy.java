package iisi.tcd.textMapper.util;

interface TextBeanStrategy {
	<T> T parseBean(String text, Class<T> clazz);

	String parseString(Object bean);
}
