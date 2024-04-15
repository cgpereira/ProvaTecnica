package backend.exception;

public class AdminException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public AdminException() {
		super();
	}
	
	public AdminException(String message) {
		super(message);
	}	

	public AdminException(String message, Exception cause) {
		super(message, cause);
	}
	
	public AdminException(Exception e) {
		super(e);
	}	

}
