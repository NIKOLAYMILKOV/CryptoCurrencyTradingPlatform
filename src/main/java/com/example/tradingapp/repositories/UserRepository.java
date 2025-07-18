package com.example.tradingapp.repositories;

import com.example.tradingapp.model.User;

public interface UserRepository {
    User findById(int id);

    User delete(int id);

    User save(User user);

    User reset(int id);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    User updateBalance(int id, double newBalance);
}
