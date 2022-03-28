package ua.com.foxminded.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Util class DateUtils
 */
public class DateUtils {

    /**
     * Empty constructor
     */
    private DateUtils() {
    }

    /**
     * Property TIME_PATTERN
     */
    private static final String TIME_PATTERN = "dd.MM.yyyy HH:mm";

    /**
     * Returns DateTimeFormatter, using property TIME_PATTERN
     *
     * @return DateTimeFormatter by pattern of TIME_PATTERN property
     */
    private static DateTimeFormatter getDefaultLocalDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(TIME_PATTERN);
    }

    /**
     * Converts param dateString to LocalDateTime object, using DateTimeFormatter
     *
     * @param dateString dateString in String type
     * @return LocalDateTime object from param dateString
     */
    public static LocalDateTime getLocalDateTimeFromString(String dateString) {
        return LocalDateTime.parse(dateString, getDefaultLocalDateTimeFormatter());
    }

    /**
     * Converts param localDateTime to String object, using DateTimeFormatter
     *
     * @param localDateTime localDateTime in LocalDateTime type
     * @return String object from param localDateTime
     */
    public static String getStringFromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(getDefaultLocalDateTimeFormatter());
    }

}
