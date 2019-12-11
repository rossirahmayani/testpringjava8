package com.rossi.testspringjava8.service;

import com.rossi.testspringjava8.common.enums.ResponseCode;
import com.rossi.testspringjava8.common.enums.Status;
import com.rossi.testspringjava8.common.utils.EncryptUtils;
import com.rossi.testspringjava8.entity.Department;
import com.rossi.testspringjava8.entity.Faculty;
import com.rossi.testspringjava8.exception.ValidationException;
import com.rossi.testspringjava8.repository.DepartmentRepository;
import com.rossi.testspringjava8.repository.FacultyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ValidationService {
    @Autowired
    private EncryptUtils encryptUtils;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Faculty validateFaculty (String facultyCode) {
        return Optional.ofNullable(facultyRepository.findByFacultyCode(facultyCode))
                .filter(faculty -> faculty.getStatus().equals(Status.ACTIVE.getCode()))
                .orElseThrow(() -> new ValidationException(ResponseCode.FACULTY_NOT_FOUND));
    }

    public Department validateDeparment (String deptCode) {
        return Optional.ofNullable(departmentRepository.findByDepartmentCode(deptCode))
                .filter(department -> department.getStatus().equals(Status.ACTIVE.getCode()))
                .orElseThrow(() -> new ValidationException(ResponseCode.DEPARTMENT_NOT_FOUND));
    }
}
