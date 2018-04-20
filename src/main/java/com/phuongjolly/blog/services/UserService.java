package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.models.requests.LoginRequest;
import com.phuongjolly.blog.models.requests.RegisterRequest;

import javax.servlet.http.HttpSession;


public interface UserService{
    User getUserByEmail(String email);
    User register(RegisterRequest registerInfo);
    User login(LoginRequest user);
}
