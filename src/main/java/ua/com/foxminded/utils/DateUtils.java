package ua.com.foxminded.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private DateUtils(){}

    private static final String TIME_PATTERN = "dd.MM.yyyy HH:mm";

    public static DateTimeFormatter getDefaultLocalDateTimeFormatter(){
        return DateTimeFormatter.ofPattern(TIME_PATTERN);
    }

    public static LocalDateTime getLocalDateTimeFromString(String dateString){
        return LocalDateTime.parse(dateString, getDefaultLocalDateTimeFormatter());
    }

}
