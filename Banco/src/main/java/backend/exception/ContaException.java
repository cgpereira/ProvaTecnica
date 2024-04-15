package backend.exception;

public class ContaException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ContaException() {
		super();
	}
	
	public ContaException(String message) {
		super(message);
	}	

	public ContaException(String message, Exception cause) {
		super(message, cause);
	}
	
	public ContaException(Exception e) {
		super(e);
	}	

}
