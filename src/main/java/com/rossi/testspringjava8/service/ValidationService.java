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

    public void validateWordSHA1(String clientWords, String ...elements){
        String elementBuild = encryptUtils.getElementWords(elements);
        String words = encryptUtils.generateWordSHA1(elementBuild);
        Optional.ofNullable(words)
                .filter(value -> !value.equalsIgnoreCase(clientWords))
                .ifPresent(value -> wordException(elementBuild, value, clientWords));
    }

    public void validateWordHMACSHA256(String clientWords, String key, String ...elements){
        String elementBuild = encryptUtils.getElementWords(elements);
        String words = encryptUtils.generateWordHMACSHA256(elementBuild, key);
        Optional.ofNullable(words)
                .filter(value -> !value.equalsIgnoreCase(clientWords))
                .ifPresent(value -> wordException(elementBuild, value, clientWords));
    }

    private void wordException(String element, String words, String clientWords){
        log.error("Invalid words");
        log.debug("wordsElements   [{}]", element);
        log.debug("words           [{}]", words);
        log.debug("clientWords     [{}]", clientWords);
        throw new ValidationException(ResponseCode.INVALID_WORDS);
    }
}
