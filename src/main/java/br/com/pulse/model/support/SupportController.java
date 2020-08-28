package br.com.pulse.model.support;

import br.com.pulse.model.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class SupportController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> notFound(Exception ex)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage()
        );

        return new ResponseEntity<ExceptionResponse>(exceptionResponse,
                HttpStatus.BAD_REQUEST);
    }
}
