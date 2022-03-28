package ua.com.foxminded.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import ua.com.foxminded.exception.ClassNotFoundException;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Optional;

/**
 * DAO class AbstractDao
 *
 * @param <T> the type of ua.com.foxminded.model package classes
 */
public abstract class AbstractDao<T> {

    /**
     * Property - logger to log important actions
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class.getName());

    /**
     * Property - generic type
     */
    private final Class<T> genericType;
    /**
     * Property - session factory
     */
    private final SessionFactory sessionFactory;
    /**
     * Property - simple class name
     */
    private final String simpleClassName;

    /**
     * Constructor autowired by SessionFactory bean.
     *
     * <p> Defines session factory, using Spring
     *
     * <p> Defines child class type, using GenericTypeResolver
     *
     * <p> Defines simple class name, using generic type for logger
     *
     * @param sessionFactory autowired SessionFactory bean
     * @throws ClassNotFoundException if class of described by AbstractDao value is not found
     * @see GenericTypeResolver#resolveTypeArgument(Class, Class)
     */
    @Autowired
    @SuppressWarnings("unchecked")
    protected AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        Class<?> classType = GenericTypeResolver.resolveTypeArgument(getClass(), AbstractDao.class);
        this.genericType = (Class<T>) classType;
        if (genericType == null) {
            throw new ClassNotFoundException("Generic type in AbstractDao is null");
        }
        this.simpleClassName = this.genericType.getSimpleName();
    }

    /**
     * Returns created object in table mapped by value class type
     *
     * @param value value to create
     * @return non-null value with defined id, described by AbstractDao
     */
    public T create(T value) {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            logger.debug("Creating new {} object", simpleClassName);
            value = genericType.cast(session.merge(value));
            logger.debug("{} object has been created", simpleClassName);
            session.getTransaction().commit();
            return value;
        }
    }


    /**
     * If a value with param id is present in table mapped by this class type,
     * returns an Optional describing the value, otherwise throws NullPointerException
     *
     * @param id id of searching in database object
     * @return an Optional describing the value of this Optional,
     * if a value with param id is present in table mapped by this class type, and the value
     * matches the given predicate, otherwise returns null
     * @throws NullPointerException if value with param id is not present in database
     */
    public Optional<T> getById(long id) {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            logger.debug("Getting {} object by id = {}", simpleClassName, id);
            T obtainedObject = session.get(genericType, id);
            logger.debug("{} object with id ={} has been obtained", simpleClassName, id);
            session.getTransaction().commit();
            return Optional.of(obtainedObject);
        }
    }

    /**
     * Returns a list of all values of type described this AbstractDao in mapped table of a database
     *
     * @return a list of all values of type described this AbstractDao in mapped table of a database
     */
    public List<T> getAll() {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            logger.debug("Getting all {} objects", simpleClassName);
            List<T> obtainedObjects = session.createQuery(
                "SELECT a FROM " + simpleClassName + " a", genericType).getResultList();
            logger.debug("All objects {} have been obtained", simpleClassName);
            session.getTransaction().commit();
            return obtainedObjects;
        }
    }

    /**
     * Deletes object with param id from the table mapped by the class described in AbstractDao,
     * if object with such param id is present in database, otherwise throws EntityNotFoundException
     *
     * @param id id of deleting from database object
     * @throws ua.com.foxminded.exception.EntityNotFoundException if object with such param id is not present in database
     */
    public void delete(long id) {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            logger.debug("Deleting {} object with id = {}", simpleClassName, id);
            T value = session.load(genericType, id);
            session.delete(value);
            logger.debug("{} object with id = {} has been deleted", simpleClassName, id);
            session.getTransaction().commit();
        }
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

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            logger.debug("Updating {} object", simpleClassName);
            session.update(value);
            logger.debug("{} object has been updated", simpleClassName);
            session.getTransaction().commit();
        }
    }
}
