package com.rossi.testspringjava8.common.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Status {
    ACTIVE ('A'),
    INACTIVE ('I');

    private Character code;

    Status (Character code){
        this.code = code;
    }
}
