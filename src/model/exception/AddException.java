package model.exception;

@SuppressWarnings("serial")
public class AddException extends Exception {
	private String errMessage;

	public AddException(String err) {
		this.errMessage = err;
	}
	public String getMessage() {
		return this.errMessage;
	}
}
