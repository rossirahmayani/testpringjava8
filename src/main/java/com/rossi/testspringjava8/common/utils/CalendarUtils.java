package com.rossi.testspringjava8.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@Slf4j
public class CalendarUtils {
    final Calendar cal = Calendar.getInstance();

    public Date getCurrentDateString(Date date){
        cal.setTime(date);
        return cal.getTime();
    }

    public Date configDateMinuteString(Date date, Integer minute){
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    public Date configDateHourString(Date date, Integer hour){
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }

    public Date configDateDayString(Date date, Integer day){
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public Date configDateMonthString(Date date, Integer month){
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public Date configDateYearString(Date date, Integer year){
        cal.setTime(date);
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    public Date parseDate(String strDate, String pattern) {
        Date date = null;
        try {
            date = DateUtils.parseDate(strDate, pattern);
        } catch (Exception ex) {
            log.warn("Unable to parse date \"{}\" with pattern \"{}\"", strDate, pattern);
        }
        return date;
    }

    public String formatDateToString(Date dt, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateString = sdf.format(dt);
        return dateString;
    }

}
