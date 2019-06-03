package model.exception;

@SuppressWarnings("serial")
public class PerformException extends Exception {
	private String errMessage;

	public PerformException(String err) {
		this.errMessage=err;

	}
	public String getMessage() {
		return this.errMessage;
	}
}
