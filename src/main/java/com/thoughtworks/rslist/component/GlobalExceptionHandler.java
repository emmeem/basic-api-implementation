package com.thoughtworks.rslist.component;

import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(Exception ex) {
        Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
        String errorMessage;
        CommenError commentError =  new CommenError();
        errorMessage = "invalid param";

        commentError.setError(errorMessage);
        logger.error("[LOGGING]: " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commentError);
    }
}
