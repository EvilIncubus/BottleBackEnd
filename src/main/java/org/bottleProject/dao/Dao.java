package org.bottleProject.dao;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();

    Long create(T t);

    T update(T t, Long id);

    void removeById(Long id);

    T findById(Long id);
}
