package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.models.requests.LoginRequest;

import javax.servlet.http.HttpSession;


public interface UserService{
    User getUserByEmail(String email);
    boolean register(User newUser);
    User login(LoginRequest user);
}
