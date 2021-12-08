package br.com.bernhoeft.meetings.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	 @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	        List<ErrorObjectDTO> errors = getErrors(ex);
	        ErrorDTO ErrorDTO = getErrorDTO(ex, status, errors);
	        return new ResponseEntity<>(ErrorDTO, status);
	    }

	    private ErrorDTO getErrorDTO(MethodArgumentNotValidException ex, HttpStatus status, List<ErrorObjectDTO> errors) {
	    	return ErrorDTO.builder().message("Requisição possui campos inválidos").code(Integer.toString(status.value())).status(status.getReasonPhrase())
	    	.objectName(ex.getBindingResult().getObjectName()).errorsObjects(errors).build();
	    }

	    private List<ErrorObjectDTO> getErrors(MethodArgumentNotValidException ex) {
	        return ex.getBindingResult().getFieldErrors().stream()
	                .map(error -> new ErrorObjectDTO(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
	                .collect(Collectors.toList());
	    }

}
