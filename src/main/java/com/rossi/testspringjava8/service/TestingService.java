package com.rossi.testspringjava8.service;

import com.rossi.testspringjava8.common.enums.ResponseCode;
import com.rossi.testspringjava8.common.utils.CalendarUtils;
import com.rossi.testspringjava8.entity.Department;
import com.rossi.testspringjava8.entity.Faculty;
import com.rossi.testspringjava8.model.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestingService {
    @Autowired
    private CalendarUtils calendarUtils;

    @Autowired
    private ValidationService validationService;

    public BaseResponse getFaculty(String facultyCode){
        Faculty faculty = validationService.validateFaculty(facultyCode);
        return generateSuccessResponse(faculty);
    }

    public BaseResponse getDept(String deptCode){
        Department department = validationService.validateDeparment(deptCode);
        return generateSuccessResponse(department);
    }

    private BaseResponse generateSuccessResponse(Object data){
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseMsg(ResponseCode.SUCCESS.getMsg());
        response.setResponseData(data);
        log.info("RESPONSE: {}", response.toString());
        return response;
    }

}
