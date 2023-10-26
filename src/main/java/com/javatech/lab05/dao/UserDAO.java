package com.javatech.lab05.dao;

import com.javatech.lab05.entity.User;
import com.javatech.lab05.service.UserService;

public class UserDAO {
    private static final UserService userService = new UserService();
    public static User login(String username, String password) {
        return userService.findByUserNameAndPassword(username, password);
    }
    public static User register(String username, String email, String password) {
        return userService.save(new User(username, email, password));
    }

}
