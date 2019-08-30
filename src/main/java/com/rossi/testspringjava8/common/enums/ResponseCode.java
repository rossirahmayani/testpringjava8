package com.rossi.testspringjava8.common.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum  ResponseCode {
    SUCCESS("0000"),
    UNKNOWN("9999");

    public String code;
    ResponseCode(String code){
        this.code = code;
    }
}
