package com.example.ticketing.repository;

import java.util.List;

public interface CrudRepository<T, ID> {

    T get(ID id);
    List<T> getAll();
    T add(T item);
    void remove(T item);
}
