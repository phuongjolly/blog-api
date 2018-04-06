package com.phuongjolly.blog.controllers;

import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.repository.UserRepository;
import com.phuongjolly.blog.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;
    private UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping
    public String addNewUser(@RequestBody User user) {
        userRepository.save(user);
        return "Saved!";
    }

    @GetMapping
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginInfo, HttpSession session) {
        User user = userService.login(loginInfo);
        HttpStatus status;

        if(user != null) {
            session.setAttribute("currentUser", user);
            User test = (User) session.getAttribute("currentUser");
            logger.info("current user: " + test.getEmail());
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(user, status);
    }

    @PostMapping("/register")
    public User register(@RequestBody User registerInfo) {
        boolean isOk = userService.register(registerInfo);
        if(isOk){
            return registerInfo;
        }
        return null;
    }

    @GetMapping("/currentUser")
    public User getCurrentUserLogin(HttpSession session) {
        return userService.getCurrentUserLogin(session);
    }

    @GetMapping("/logout")
    public User logout(HttpSession session) {
        User data = (User) session.getAttribute("currentUser");
        if(data != null) {
            logger.info("is removing the current user");
            session.removeAttribute("currentUser");
        }
        return data;
    }

    @PostMapping("/update")
    public User update(@RequestBody User userInfo){
        User user = userRepository.findByEmail(userInfo.getEmail());
        user.setName(userInfo.getName());
        user.setAvatarUrl(userInfo.getAvatarUrl());
        logger.info("avatar before save: " + user.getAvatarUrl());
        return userRepository.save(user);
    }
}
