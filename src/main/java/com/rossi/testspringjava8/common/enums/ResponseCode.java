package com.rossi.testspringjava8.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@ToString
@AllArgsConstructor
public enum  ResponseCode {
    SUCCESS("0000", "Success"),
    INSUFFICIENT_PARAMS("0001", "Insufficient params"),
    INVALID_WORDS("0002", "Invalid words"),
    FACULTY_NOT_FOUND("0010", "Faculty is not found"),
    FACULTY_ALREADY_REGISTERED("0011", "Faculty is already registered"),
    DEPARTMENT_NOT_FOUND("0020", "Department is not found"),
    DEPARTMENT_ALREADY_REGISTERED("0021", "Department is already registered"),
    ERROR_UNKNOWN("9999", "Unexpected error");

    private String code;
    private String msg;

    public static ResponseCode byCode(String code) {
        Predicate<ResponseCode> isEqual = responseCode -> Optional.ofNullable(responseCode)
                .map(thisObject -> thisObject.getCode())
                .map(code::equals)
                .orElse(false);
        return Stream.of(values())
                .filter(isEqual)
                .findAny()
                .orElse(null);
    }
}
