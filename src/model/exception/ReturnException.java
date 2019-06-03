package model.exception;

@SuppressWarnings("serial")
public class ReturnException extends Exception {
	private String errMessage;

	public ReturnException(String err) {
		this.errMessage = err;
	}
	public String getMessage() {
		return this.errMessage;
	}
}
