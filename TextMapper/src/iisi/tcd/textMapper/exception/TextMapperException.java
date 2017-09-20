package iisi.tcd.textMapper.exception;

import java.lang.reflect.Field;

public class TextMapperException extends RuntimeException {
	private TextMapperError error;

	public TextMapperException(TextMapperError error, Field field) {
		super(getErrorMsg(error, field));
		this.error = error;
	}

	public TextMapperException(TextMapperError error) {
		super(getErrorMsg(error));
		this.error = error;
	}

	public TextMapperException(TextMapperError error, Throwable cause) {
		super(getErrorMsg(error), cause);
	}

	private static String getErrorMsg(TextMapperError error) {
		if (error == null) {
			return "TextMapperError is null";
		}
		return error.getMessage();
	}

	private static String getErrorMsg(TextMapperError error, Field field) {
		if (error == null) {
			return "TextMapperError is null";
		}
		if (field != null) {
			return field.getName() + " : " + error.getMessage();
		} else {
			return getErrorMsg(error);
		}
	}

	public TextMapperError getError() {
		return error;
	}

}
