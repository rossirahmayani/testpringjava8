package com.rossi.testspringjava8.exception;

import com.rossi.testspringjava8.common.enums.ResponseCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rossirahmayani
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -475169856298292548L;

    private String message;
    private ResponseCode responseCode;

    public ValidationException(ResponseCode responseCode){this.responseCode = responseCode;}
    public ValidationException(ResponseCode responseCode, String message){
        this.responseCode = responseCode;
        this.message = message;
    }
}