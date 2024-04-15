package backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = { ApiException.class })
	protected ResponseEntity<Object> naoEncontrado(ApiException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		apiError.setDebugMessage((ex.getCause()!=null?ex.getCause().getMessage():""));
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(value = { TransacaoException.class })
	protected ResponseEntity<Object> naoEncontrado(TransacaoException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		apiError.setDebugMessage((ex.getCause()!=null?ex.getCause().getMessage():""));
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(value = { ContaException.class })
	protected ResponseEntity<Object> naoEncontrado(ContaException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		apiError.setDebugMessage((ex.getCause()!=null?ex.getCause().getMessage():""));
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(value = { AdminException.class })
	protected ResponseEntity<Object> naoEncontrado(AdminException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		apiError.setDebugMessage((ex.getCause()!=null?ex.getCause().getMessage():""));
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
