package com.example.tradingapp.services;

import com.example.tradingapp.exceptions.BadRequestException;
import com.example.tradingapp.exceptions.UnauthorisedException;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.dtos.LoginUserDTO;
import com.example.tradingapp.model.dtos.RegisterUserDTO;
import com.example.tradingapp.model.dtos.ResponseUserDTO;
import com.example.tradingapp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    public ResponseUserDTO login(LoginUserDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        validateUsernameAndPassword(username, password);
        User u = userRepository.findByUsername(username);
        if (u == null) {
            throw new UnauthorisedException("Wrong credentials");
        }

        if (!password.equals(u.getPassword())) {
            throw new UnauthorisedException("Wrong credentials");
        }
        return mapper.map(u, ResponseUserDTO.class);
    }

    public ResponseUserDTO addUser(RegisterUserDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();

        validateUsernameAndPassword(username, password);
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("User with that username already exists");
        }
        if (confirmPassword == null || confirmPassword.isBlank()) {
            throw new BadRequestException("Confirm password is mandatory");
        }
        if (!confirmPassword.equals(password)) {
            throw new BadRequestException("Confirm password and password does not match");
        }
        User u = userRepository.save(mapper.map(dto, User.class));
        return mapper.map(u, ResponseUserDTO.class);
    }

    public ResponseUserDTO getById(int id) {
        validateId(id);
        User u = userRepository.findById(id);
        return mapper.map(u, ResponseUserDTO.class);
    }

    public ResponseUserDTO delete(int id) {
        validateId(id);
        User u = userRepository.delete(id);
        return mapper.map(u, ResponseUserDTO.class);
    }

    public ResponseUserDTO reset(int id) {
        validateId(id);
        User u = userRepository.reset(id);
        return mapper.map(u, ResponseUserDTO.class);
    }

    public User updateBalance(int id, double newBalance) {
        return userRepository.updateBalance(id, newBalance);
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new BadRequestException("Id cannot be non-positive");
        }
    }

    private void validateUsernameAndPassword(String username, String password) {
        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is mandatory");
        }
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username is mandatory");
        }
    }
}