package iisi.tcd.textMapper.exception;

public class TextMapperException extends RuntimeException {
	private TextMapperError error;

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

	public TextMapperError getError() {
		return error;
	}

}
