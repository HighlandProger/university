package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import ua.com.foxminded.dao.AbstractDao;
import ua.com.foxminded.exception.ClassNotFoundException;
import ua.com.foxminded.exception.EntityNotFoundException;

import javax.persistence.OptimisticLockException;
import java.util.List;

/**
 * Service class AbstractService
 *
 * @param <T> the type of ua.com.foxminded.model package classes
 */
public abstract class AbstractService<T> {

    /**
     * Property - abstract dao
     */
    private final AbstractDao<T> abstractDao;
    /**
     * Property - simple class name
     */
    private final String simpleClassName;

    /**
     * Constructor autowired by AbstractDao bean.
     *
     * <p> Defines abstract dao, using Spring
     *
     * <p> Defines child class type using GenericTypeResolver
     *
     * <p> Defines simple class name using generic type for logger
     *
     * @param abstractDao autowired AbstractDao bean
     * @throws ClassNotFoundException if child class is null
     * @see GenericTypeResolver#resolveTypeArgument(Class, Class)
     */
    @Autowired
    protected AbstractService(AbstractDao<T> abstractDao) {
        this.abstractDao = abstractDao;
        Class<?> genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), AbstractService.class);
        if (genericClass == null) {
            throw new ClassNotFoundException("Cannot find AbstractService class");
        }
        this.simpleClassName = genericClass.getSimpleName();
    }

    /**
     * Returns created object in table mapped by value class type by calling AbstractDao.create(value)
     *
     * @param value value to create
     * @return non-null value with defined id, described by AbstractService
     */
    public T create(T value) {
        return abstractDao.create(value);
    }

    /**
     * Returns a value, described in AbstractService by calling AbstractDao.getById(id)
     * and throws EntityNotFoundException if object with such id is not found in database
     *
     * @param id id of searching in database object
     * @return the non-null value described in AbstractService
     * @throws EntityNotFoundException if object with such id is not found in database
     */
    public T getById(Long id) {
        return abstractDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Object " + simpleClassName + " with id=" + id + " is not found"));
    }

    /**
     * Returns a list of all values of type described this AbstractService in mapped table of a database
     *
     * @return a list of all values of type described this AbstractService in mapped table of a database
     */
    public List<T> getAll() {
        return abstractDao.getAll();
    }

    /**
     * Deletes object with param id from the table mapped by the class described in AbstractService,
     * if object with such param id is present in database, otherwise throws EntityNotFoundException
     *
     * @param id id of deleting from database object
     * @throws ua.com.foxminded.exception.EntityNotFoundException if object with such param id is not present in database
     * @see EntityNotFoundException
     */
    public void delete(Long id) {
        abstractDao.delete(id);
    }

    /**
     * Updates object in table mapped by class type if object with id defined in param value is present,
     * otherwise throws OptimisticLockException
     *
     * @param value value to update object in database with such value's id
     * @throws OptimisticLockException if object with id defined in param value
     *                                 is not present in database
     */
    public void update(T value) {
        abstractDao.update(value);
    }
}
