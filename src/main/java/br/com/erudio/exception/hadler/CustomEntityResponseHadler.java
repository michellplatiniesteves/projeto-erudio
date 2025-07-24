package br.com.erudio.exception.hadler;

import br.com.erudio.exception.ExceptionResponse;
import br.com.erudio.exception.FileNotFoundException;
import br.com.erudio.exception.FileStorageException;
import br.com.erudio.exception.UnsupportedMathOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomEntityResponseHadler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public  final ResponseEntity<ExceptionResponse> hadlerAllException(Exception e, WebRequest request){

        ExceptionResponse response = new ExceptionResponse(new Date(),e.getMessage(),request.getDescription(false));
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
    @ExceptionHandler(UnsupportedMathOperationException.class)
    public  final ResponseEntity<ExceptionResponse> hadlerBadRequestException(Exception e, WebRequest request){

        ExceptionResponse response = new ExceptionResponse(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST) ;
    }
    @ExceptionHandler(FileStorageException.class)
    public  final ResponseEntity<ExceptionResponse> hadlerFileStorageException(Exception e, WebRequest request){

        ExceptionResponse response = new ExceptionResponse(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
    @ExceptionHandler(FileNotFoundException.class)
    public  final ResponseEntity<ExceptionResponse> hadlerFileNotFoundException(Exception e, WebRequest request){

        ExceptionResponse response = new ExceptionResponse(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND) ;
    }
}
