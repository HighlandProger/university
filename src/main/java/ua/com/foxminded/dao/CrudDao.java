package ua.com.foxminded.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    T create(T value);

    Optional<T> getById(long id);

    List<T> getAll();

    void delete(long id);

    void update(long id, T value);
}
