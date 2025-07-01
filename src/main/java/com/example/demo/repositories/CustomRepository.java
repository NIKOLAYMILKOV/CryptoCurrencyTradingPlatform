package com.example.demo.repositories;

public interface CustomRepository<T> {
    T findById(int id);

    T delete(int id);

    T save(T user);

    T reset(int id);

    T findByUsername(String username);

    boolean existsByUsername(String username);

}
