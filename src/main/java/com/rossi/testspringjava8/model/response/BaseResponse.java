package com.rossi.testspringjava8.model.response;

import lombok.Builder;
import lombok.Data;

@Data
public class BaseResponse {
    private String responseCode;
    private String responseMsg;
}
