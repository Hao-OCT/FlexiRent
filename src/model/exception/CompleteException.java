package model.exception;

@SuppressWarnings("serial")
public class CompleteException extends Exception {
	private String errMessage;

	public CompleteException(String err) {
		this.errMessage = err;
	}

	public String getMessage() {
		return this.errMessage;
	}
}
