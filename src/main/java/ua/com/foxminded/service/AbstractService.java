package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import ua.com.foxminded.dao.AbstractDao;
import ua.com.foxminded.exception.ClassNotFoundException;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

public abstract class AbstractService<T> {

    private final AbstractDao<T> abstractDao;
    private final String simpleClassName;

    @Autowired
    protected AbstractService(AbstractDao<T> abstractDao) {
        this.abstractDao = abstractDao;
        Class<?> genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), AbstractService.class);
        if (genericClass == null){
            throw new ClassNotFoundException("Cannot find AbstractService class");
        }
        this.simpleClassName = genericClass.getSimpleName();
    }

    public T create(T value) {
        return abstractDao.create(value);
    }

    public T getById(Long id) {
        return abstractDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Object " + simpleClassName + " with id=" + id + " is not found"));
    }

    public List<T> getAll() {
        return abstractDao.getAll();
    }

    public void delete(Long id) {
        abstractDao.delete(id);
    }

    public void update(Long id, T value) {
        abstractDao.update(id, value);
    }
}
