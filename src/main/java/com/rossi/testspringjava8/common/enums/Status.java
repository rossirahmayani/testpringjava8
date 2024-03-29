package com.rossi.testspringjava8.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Status {
    ACTIVE ('A'),
    INACTIVE ('I');

    private Character code;

}
