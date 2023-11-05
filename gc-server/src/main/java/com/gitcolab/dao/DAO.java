package com.gitcolab.dao;

import java.util.Optional;

public interface DAO<T> {
    Optional<T> get(long id);
    int save(T t);
    int update(T t);
    void delete(long id);
}
