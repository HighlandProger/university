package ua.com.foxminded.exception;

/**
 * Exception class ClassNotFoundException for AbstractDao
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Throws new Runtime exception while there is no entity with such id in database
     *
     * @param message information about exception
     * @see ua.com.foxminded.service.AbstractService#getById(Long)
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
