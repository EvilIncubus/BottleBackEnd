package org.example.dao;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();

    long create(T t);

    T update(T t, Long id);

    Long removeById(Long id);

    T findById(Long id);
}
