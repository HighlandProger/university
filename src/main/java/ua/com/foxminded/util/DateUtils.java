package ua.com.foxminded.util;

import ua.com.foxminded.exception.DateParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";

    private DateUtils() {
    }

    public static String getDateStringFromMilliseconds(long dateInMilliseconds) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_TIME_PATTERN);
        return dateFormatter.format(dateInMilliseconds);
    }

    public static long getMillisecondsFromDateString(String dateString) {
        return getDateFromDateString(dateString).getTime();
    }

    private static Date getDateFromDateString(String dateString) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_TIME_PATTERN);
        Date date;

        try {
            date = dateFormatter.parse(dateString);
        } catch (ParseException e) {
            throw new DateParseException("Cannot read " + dateString + ". Check date string");
        }

        return date;
    }
}
