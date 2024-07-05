package com.example.ticketing.repository;

public interface RepositoryInitializer<T extends Repository<?, ?>> {

    void initialize();

}
