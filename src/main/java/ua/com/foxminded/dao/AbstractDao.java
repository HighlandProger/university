package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.exception.ClassNotFoundException;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class.getName());

    private final Class<T> genericType;
    private final JdbcTemplate jdbcTemplate;
    private final String simpleClassName;

    @Autowired
    @SuppressWarnings("unchecked")
    protected AbstractDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        Class<?> classType = GenericTypeResolver.resolveTypeArgument(getClass(), AbstractDao.class);
        this.genericType = (Class<T>) classType;
        if (genericType == null){
            throw new ClassNotFoundException("Generic type in AbstractDao is null");
        }
        this.simpleClassName = this.genericType.getSimpleName();
    }

    protected abstract Object[] getParams(T value);

    protected abstract String getCreateObjectSql();

    protected abstract String getObjectByIdSql();

    protected abstract String getAllObjectsSql();

    protected abstract String getDeleteObjectSql();

    protected abstract String getUpdateObjectSql();

    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public T create(T value) {

        logger.debug("Creating new {} object", simpleClassName);
        T createdValue = jdbcTemplate.queryForObject(getCreateObjectSql(), new BeanPropertyRowMapper<>(genericType), getParams(value));
        logger.debug("{} object has been created", simpleClassName);
        return createdValue;
    }

    public Optional<T> getById(long id) {

        logger.debug("Getting {} object by id = {}", simpleClassName, id);
        try {
            T obtainedObject = jdbcTemplate.queryForObject(getObjectByIdSql(), new BeanPropertyRowMapper<>(genericType), id);
            logger.debug("{} object with id ={} has been obtained", simpleClassName, id);
            return Optional.ofNullable(obtainedObject);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("Couldn't find {} object with id = {}", simpleClassName, id);
            return Optional.empty();
        }
    }

    public List<T> getAll() {

        logger.debug("Getting all {} objects", simpleClassName);
        List<T> obtainedObjects = jdbcTemplate.query(getAllObjectsSql(), new BeanPropertyRowMapper<>(genericType));
        logger.debug("All objects {} have been obtained", simpleClassName);
        return obtainedObjects;
    }

    public void delete(long id) {

        logger.debug("Deleting {} object with id = {}", simpleClassName, id);
        jdbcTemplate.update(getDeleteObjectSql(), id);
        logger.debug("{} object with id = {} has been deleted", simpleClassName, id);
    }

    public void update(long id, T value) {

        Object[] paramsWithId = Arrays.copyOf(getParams(value), getParams(value).length + 1);
        paramsWithId[paramsWithId.length - 1] = id;
        logger.debug("Updating {} object with id = {}", simpleClassName, id);
        jdbcTemplate.update(getUpdateObjectSql(), paramsWithId);
        logger.debug("{} object with id = {} has been updated", simpleClassName, id);
    }
}
