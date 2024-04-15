package backend.exception;

public class TransacaoException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public TransacaoException() {
		super();
	}
	
	public TransacaoException(String message) {
		super(message);
	}	

	public TransacaoException(String message, Exception cause) {
		super(message, cause);
	}
	
	public TransacaoException(Exception e) {
		super(e);
	}	

}
