package com.phuongjolly.blog.models.requests;

import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.services.UserDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginRequest {

    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
