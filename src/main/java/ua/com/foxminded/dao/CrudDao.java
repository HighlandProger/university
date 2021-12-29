package ua.com.foxminded.dao;

import java.util.List;

public interface CrudDao<T> {

    T create(T value);

    T getById(long id);

    List<T> getAll();

    void delete(long id);
}
