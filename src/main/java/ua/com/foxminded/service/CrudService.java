package ua.com.foxminded.service;

import java.util.List;

public interface CrudService<T> {

    T create(T value);

    T getById(Long id);

    List<T> getAll();

    void delete(Long id);
}
