package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.Role;
import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.models.requests.LoginRequest;
import com.phuongjolly.blog.models.requests.RegisterRequest;

import javax.servlet.http.HttpSession;
import java.util.List;


public interface UserService{
    User getUserByEmail(String email);
    User register(RegisterRequest registerInfo);
    User login(LoginRequest user);
    Iterable<Role> addRole(Role role);
    User setAdmin(User requestInfo);
}
