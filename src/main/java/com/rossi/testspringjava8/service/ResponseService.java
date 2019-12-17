package com.rossi.testspringjava8.service;

import com.rossi.testspringjava8.common.enums.ResponseCode;
import com.rossi.testspringjava8.model.response.BaseResponse;
import com.rossi.testspringjava8.model.response.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResponseService {
    public DataResponse generateSuccessResponse(Object data){
        DataResponse response = new DataResponse();
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseMsg(ResponseCode.SUCCESS.getMsg());
        response.setResponseData(data);
        log.info("RESPONSE: {}", response.toString());
        return response;
    }

    public BaseResponse generateSuccessResponse(){
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseMsg(ResponseCode.SUCCESS.getMsg());
        log.info("RESPONSE: {}", response.toString());
        return response;
    }
}
