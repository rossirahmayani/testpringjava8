package com.rossi.testspringjava8.service;

import com.rossi.testspringjava8.common.enums.ResponseCode;
import com.rossi.testspringjava8.common.enums.Status;
import com.rossi.testspringjava8.common.utils.CalendarUtils;
import com.rossi.testspringjava8.entity.Department;
import com.rossi.testspringjava8.entity.Faculty;
import com.rossi.testspringjava8.model.request.TestingRequest;
import com.rossi.testspringjava8.model.response.BaseResponse;
import com.rossi.testspringjava8.model.response.DataResponse;
import com.rossi.testspringjava8.repository.FacultyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestingService {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private FacultyRepository facultyRepository;

    public DataResponse getFaculty(String facultyCode){
        Faculty faculty = validationService.validateFaculty(facultyCode);
        return responseService.generateSuccessResponse(faculty);
    }

    public DataResponse getDept(String deptCode){
        Department department = validationService.validateDeparment(deptCode);
        return responseService.generateSuccessResponse(department);
    }

    public BaseResponse addFaculty(TestingRequest request){
        Faculty faculty = new Faculty();
        faculty.setStatus(Status.ACTIVE.getCode());
        facultyRepository.save(faculty);
        return responseService.generateSuccessResponse();
    }


}
