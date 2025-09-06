package br.com.erudio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class IvalidJWTAuthenticationException extends Exception{

    public IvalidJWTAuthenticationException(String message) {
        super(message);
    }

    public IvalidJWTAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
