package com.example.demo.repositories;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

//@CustomRepository
public interface UserRepository {
    User findById(int id);

    User delete(int id);

    User save(User user);

    User reset(int id);

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
