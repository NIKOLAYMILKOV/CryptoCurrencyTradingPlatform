package com.example.tradingapp.controllers;

import com.example.tradingapp.exceptions.UnauthorisedException;
import com.example.tradingapp.model.dtos.LoginUserDTO;
import com.example.tradingapp.model.dtos.RegisterUserDTO;
import com.example.tradingapp.model.dtos.ResponseUserDTO;
import com.example.tradingapp.services.UserService;
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

    @DeleteMapping("/remove")
    public ResponseUserDTO delete(HttpSession session) {
        validateLogged(session);
        int id = (int) session.getAttribute(USER_ID);
        return userService.delete(id);
    }

    @PutMapping("/reset")
    public ResponseUserDTO reset(HttpSession session) {
        validateLogged(session);
        int id = (int) session.getAttribute(USER_ID);
        return userService.reset(id);
    }

    private void validateLogged(HttpSession session) {
        if (session.getAttribute(LOGGED) == null || !(boolean)session.getAttribute(LOGGED)) {
            throw new UnauthorisedException("Log in first");
        }
    }
}
