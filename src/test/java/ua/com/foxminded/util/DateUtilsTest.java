package ua.com.foxminded.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

    @Test
    void getDateStringFromMilliseconds() {

        long dateInMilliseconds = 884023500000L;

        String expectedString = "05.01.1998 23:05";
        String actualString = DateUtils.getDateStringFromMilliseconds(dateInMilliseconds);

        assertEquals(expectedString, actualString);
    }

    @Test
    void getMillisecondsFromDateString() {

        String dateString = "05.01.1998 23:05";

        long expectedMilliseconds = 884023500000L;
        long actualMilliseconds = DateUtils.getMillisecondsFromDateString(dateString);

        assertEquals(expectedMilliseconds, actualMilliseconds);
    }
}
