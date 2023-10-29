package com.user.exception;

import com.common.constant.error.ErrorCode;
import com.common.constant.error.ErrorConstant;
import com.common.exception.HandleNotFoundException;
import com.common.exception.HandledInternalServerException;
import com.common.exception.global.HandledApiErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HandledApiErrors> handleInvalidArgumentException(MethodArgumentNotValidException ex) {

        List<String> collect = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            // errorMap.put(error.getField(), error.getDefaultMessage());
            collect.add(error.getDefaultMessage());
        });
        HandledApiErrors apiError = new HandledApiErrors(ErrorCode.USER_ERROR.toString(), collect);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandleNotFoundException.class)
    @ResponseBody
    public ResponseEntity<HandledApiErrors> handleNotFoundException(HandleNotFoundException ex) {
        HandledApiErrors apiError = null;
        String errMsg = (ex.getMessage() != null) ? ex.getMessage() : HttpStatus.NOT_FOUND.getReasonPhrase();
        apiError = new HandledApiErrors(ErrorCode.USER_ERROR.toString(), Arrays.asList(errMsg));
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(HandledInternalServerException.class)
    @ResponseBody
    public ResponseEntity<HandledApiErrors> handleInternalServerException(HandledInternalServerException ex) {
        String errMsg = (ex.getMessage() != null) ? ex.getMessage() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        String errorSplit[] = errMsg.split(ErrorConstant.ERROR_BREAK);
        HandledApiErrors apiError = new HandledApiErrors(ex.getErrorType(), Arrays.asList(errorSplit));
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
