package com.example.ticketing.repository;

public interface ModelRepositoryInitializer<T extends ModelRepository<?, ?>> {

    void initialize();

}
