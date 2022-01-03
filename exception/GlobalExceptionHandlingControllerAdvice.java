
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

import br.com.bernhoeft.meetings.service.MessageService;
import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandlingControllerAdvice extends ResponseEntityExceptionHandler{

	private final MessageService messageService;

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorDTO errorHandlerw(Exception ex) throws Exception {
		return ErrorDTO.builder()
				.code("500")
				.error(ex.toString())
				.build();
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ErrorDTO errorHandlerw(RuntimeException ex) throws Exception {
		return ErrorDTO.builder()
				.code("500")
				.error(ex.getMessage())
				.message(ex.getMessage())
				.build();
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ErrorDTO errorHandlerw(DataIntegrityViolationException ex) throws Exception {
		return ErrorDTO.builder()
				.code("500")
				.error(ex.toString())
				.message("Erro de restrição no banco de dados")
				.build();
	}
	
//	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	@ResponseBody
//	public ErrorDTO errorHandler(HttpMessageNotReadableException ex) throws Exception {
//		String message = this.messageService.getMessage("error.system.http.message.not.readable");
//		return ErrorDTO.builder()
//				.code("400")
//				.error(message)
//				.build();
//	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ErrorDTO errorHandler(BusinessException ex) throws Exception {
		return ErrorDTO.builder()
				.code("400")
				.error(ex.getMessage())
				.build();
	}
	
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public ErrorDTO errorHandler(ResourceNotFoundException ex) throws Exception {
		return ErrorDTO.builder()
				.code("404")
				.error(ex.getMessage())
				.build();
	}
	
	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO numberFormatException(NumberFormatException ex) throws Exception {
		return ErrorDTO.builder()
				.code("400")
				.error(ex.getMessage())
				.build();
	}
	
	@ExceptionHandler(InvalidDefinitionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO invalidDefinitionException(InvalidDefinitionException ex) throws Exception {
		String message = this.messageService.getMessage("com.fasterxml.jackson.databind.exc.invalid_definition_exception", 
				ex.getPathReference() );
		return ErrorDTO.builder()
				.code("400")
				.error(message)
				.build();
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorDTO unauthorized(AccessDeniedException ex) throws Exception {
		
		return ErrorDTO.builder()
				.code("403")
				.error(ex.getMessage())
				.message("Você não possui privilégios mínimos para realizar esta ação")
				.build();
	}

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ResponseBody
//	public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
//		BindingResult result = ex.getBindingResult();
//		FieldError fieldError = result.getFieldError();
//		
//		List<String> args = new ArrayList<String>();
//		for (int i = fieldError.getArguments().length-1; i >= 1; i--) {
//			Object arg = fieldError.getArguments()[i];
//			args.add(arg.toString());
//		} 
//		
//		String message = this.messageService.getMessage(fieldError.getDefaultMessage(), 
//				args.toArray(new String[args.size()]));
//		return ErrorDTO.builder()
//				.code("400")
//				.error(message)
//				.build();
//	}
	
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
