package br.com.f1rst3.cadprodutos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    public ValidationException(String msg) {
        super(msg);
    }

}
