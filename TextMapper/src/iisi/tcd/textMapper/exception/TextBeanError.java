package iisi.tcd.textMapper.exception;

public enum TextBeanError implements TextMapperError {
	NotTextBeanError("此物件沒有TextBean Annotation"), 
	TypeNotSupport("物件的property只能是String,Integer(int),List"), 
	ParseError("解析錯誤");
	private String message;

	private TextBeanError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
