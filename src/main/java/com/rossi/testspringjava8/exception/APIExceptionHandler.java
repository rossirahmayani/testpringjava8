package com.rossi.testspringjava8.exception;

import com.rossi.testspringjava8.common.enums.ResponseCode;
import com.rossi.testspringjava8.common.utils.EncryptUtils;
import com.rossi.testspringjava8.common.utils.JsonUtils;
import com.rossi.testspringjava8.model.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class APIExceptionHandler {

    @Autowired
    private Validator validator;

    @Autowired
    EncryptUtils utils;

    @Autowired
    JsonUtils jsonUtils;

    @Autowired
    MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseResponse> handleValidationException(ValidationException ex){
        log.warn("Validation API exception caught: {}", ex.getMessage());
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ex.getResponseCode().getCode());
        String message = Optional.ofNullable(ex.getMessage())
                .filter(val -> !val.isEmpty())
                .orElse(messageSource.getMessage(ex.getResponseCode().getCode(), null, LocaleContextHolder.getLocale()));
        response.setResponseMsg(message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleIllegalArgumentException(IllegalArgumentException ex){
        log.warn("IllegalArgumentException caught: {}", ex.getMessage());
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ex.getMessage());
        String message = Optional.ofNullable(ResponseCode.byCode(ex.getMessage()))
                .map(responseCode-> messageSource.getMessage(responseCode.getCode(), null, LocaleContextHolder.getLocale()))
                .orElse(ex.getMessage());
        response.setResponseMsg(message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<BaseResponse> handleIllegalArgumentException(IllegalStateException ex){
        log.warn("IllegalStateException caught: {}", ex.getMessage());
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ex.getMessage());
        String message = Optional.ofNullable(ResponseCode.byCode(ex.getMessage()))
                .map(responseCode-> messageSource.getMessage(responseCode.getCode(), null, LocaleContextHolder.getLocale()))
                .orElse(ex.getMessage());
        response.setResponseMsg(message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handlerPostgresException(ConstraintViolationException ex){
        log.warn("Validation API exception caught: {}", ex.getMessage());
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ex.getSQLException().getSQLState());
        response.setResponseMsg(ex.getLocalizedMessage());
        MDC.clear();
        log.info("RESPONSE_FORM", jsonUtils.toJsonString(response));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException ex) {
        log.warn("Handle BindException ...");
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ResponseCode.INSUFFICIENT_PARAMS.getCode());
        response.setResponseMsg(messageSource.getMessage(utils.getSingleErrorMessage(ex.getFieldErrors(), ex.getGlobalErrors()), null, LocaleContextHolder.getLocale()));
        log.info("RESPONSE_FORM", jsonUtils.toJsonString(response));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> unknownException(Exception ex) {
        log.error("Handle Unkown Exception => {} ...", ex);
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ResponseCode.ERROR_UNKNOWN.getCode());
        response.setResponseMsg("Internal Server Error");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
