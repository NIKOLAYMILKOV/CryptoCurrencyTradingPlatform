package com.example.demo.controllers;

import com.example.demo.exceptions.UnauthorisedException;
import com.example.demo.model.dtos.LoginUserDTO;
import com.example.demo.model.dtos.RegisterUserDTO;
import com.example.demo.model.dtos.ResponseUserDTO;
import com.example.demo.servises.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {
    public static final String LOGGED = "logged";
    public static final String USER_ID = "user_id";
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseUserDTO register(@RequestBody RegisterUserDTO user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public ResponseUserDTO login(@RequestBody LoginUserDTO userDTO, HttpSession session) {
        ResponseUserDTO dto = userService.login(userDTO);
        session.setAttribute(LOGGED, true);
        session.setAttribute(USER_ID, dto.getId());
        return dto;
    }

    @GetMapping("/users/{userId}")
    public ResponseUserDTO getById(@PathVariable int userId, HttpSession session) {
        validateLogged(session);
        return userService.getById(userId);
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseUserDTO delete(@PathVariable int userId, HttpSession session) {
        validateLogged(session);
        return userService.delete(userId);
    }

    @PutMapping("/reset")
    public ResponseUserDTO reset(HttpSession session) {
        validateLogged(session);
        int id = (int) session.getAttribute(USER_ID);
        return userService.reset(id);
    }

    private void validateLogged(HttpSession session) {
        if (!session.getAttribute(LOGGED).equals(true)) {
            throw new UnauthorisedException("Log in first");
        }
    }
}
