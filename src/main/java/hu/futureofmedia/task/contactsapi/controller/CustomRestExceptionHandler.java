package hu.futureofmedia.task.contactsapi.controller;
import hu.futureofmedia.task.contactsapi.DTO.ApiError;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectEmailException;
import hu.futureofmedia.task.contactsapi.exceptions.IncorrectUserNameException;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(
           MethodArgumentNotValidException ex,
           HttpHeaders headers,
           HttpStatus status,
           WebRequest request) {
       List<String> errors = new ArrayList<String>();
       for (FieldError error : ex.getBindingResult().getFieldErrors()) {
           errors.add(error.getField() + ": " + error.getDefaultMessage());
       }
       for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
           errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
       }

       ApiError apiError =
               new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
       logger.error("Fault in validation");
       return handleExceptionInternal(
               ex, apiError, headers, apiError.getStatus(), request);
   }
    @ExceptionHandler({ RecordNotFoundException.class, SQLException.class, IOException.class})
    protected ResponseEntity<Object> handleNotFound(
            Exception ex, WebRequest request) {
        logger.error("Can't found the record "+ ex.getMessage().toString());
        return handleExceptionInternal(ex, "Not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler({ IncorrectEmailException.class, IncorrectUserNameException.class, BadCredentialsException.class})
    protected ResponseEntity<Object> handleWrongUserNameOrEmail(
            Exception ex, WebRequest request) {
        logger.error("Wrong user name or email");
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }
}
