package com.rossi.testspringjava8.service;

import com.rossi.testspringjava8.common.utils.CalendarUtils;
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

}
