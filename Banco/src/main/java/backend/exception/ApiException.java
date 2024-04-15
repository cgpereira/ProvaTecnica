package backend.exception;

public class ApiException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ApiException() {
		super();
	}
	
	public ApiException(String message) {
		super(message);
	}

	public ApiException(String message, Exception cause) {
		super(message, cause);
	}
	
	public ApiException(Exception e) {
		super(e);
	}	

}