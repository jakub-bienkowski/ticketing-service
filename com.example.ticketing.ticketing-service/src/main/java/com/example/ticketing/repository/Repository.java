package com.example.ticketing.repository;

import java.util.List;

public interface Repository<T, ID> {

    void fillRepository(List<T> items);
    T get(ID id);

}
