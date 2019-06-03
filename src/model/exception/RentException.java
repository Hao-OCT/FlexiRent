package model.exception;

@SuppressWarnings("serial")
public class RentException extends Exception {
	private String errMessage;

	public RentException(String err) {
		this.errMessage = err;
	}
	public String getMessage() {
		return this.errMessage;
	}
}
