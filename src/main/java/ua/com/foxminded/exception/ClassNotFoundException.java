package ua.com.foxminded.exception;

/**
 * Exception class ClassNotFoundException for AbstractDao
 *
 * @see ua.com.foxminded.dao.AbstractDao
 */
public class ClassNotFoundException extends RuntimeException {

    /**
     * Throws new Runtime exception while there is no class with such name
     *
     * @param message information about exception
     */
    public ClassNotFoundException(String message) {
        super(message);
    }
}
