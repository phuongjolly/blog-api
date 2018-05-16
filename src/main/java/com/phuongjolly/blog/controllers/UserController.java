package com.phuongjolly.blog.controllers;

import com.phuongjolly.blog.models.ErrorResponse;
import com.phuongjolly.blog.models.Role;
import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.models.requests.LoginRequest;
import com.phuongjolly.blog.models.requests.RegisterRequest;
import com.phuongjolly.blog.repository.UserRepository;
import com.phuongjolly.blog.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;
    private UserService userService;

    public UserController(UserRepository userRepository,
                          UserService userService) {
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
    public ResponseEntity login(@RequestBody LoginRequest request) {
        User user = userService.login(request);
        HttpStatus status;

        if(user != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword(),
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            //User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            status = HttpStatus.OK;
            return new ResponseEntity<>(user, status);
        } else {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(new ErrorResponse(403, "Login failed. Username or Password is incorrect"),
                    status);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerInfo) {
        User user;
        user = userService.getUserByEmail(registerInfo.getEmail());
        HttpStatus status;
        if(user != null) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new ErrorResponse(400, "User exist already"), status);
        } else {
            user =  userService.register(registerInfo);
            if(user != null) {
                status = HttpStatus.OK;
                return new ResponseEntity<>(user, status);
            } else {
                status = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<>(new ErrorResponse(400, "User exist already"), status);
            }
        }

    }

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET, produces={"application/json"})
    @ResponseBody
    public User getCurrentUserLogin(Principal principal) {
        if (principal != null) {
            return userService.getUserByEmail(principal.getName());
        } else {
            return null;
        }
    }

    @PostMapping("/logout")
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession();
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
        return true;
    }

    @PostMapping("/update")
    public User update(@RequestBody User userInfo){
        User user = userRepository.findByEmail(userInfo.getEmail());
        user.setName(userInfo.getName());
        user.setAvatarUrl(userInfo.getAvatarUrl());
        logger.info("avatar before save: " + user.getAvatarUrl());
        return userRepository.save(user);
    }

    @PostMapping("/setAdmin")
    public User setAdmin(@RequestBody User requestInfo) {
        return userService.setAdmin(requestInfo);
    }

    @GetMapping("/isAdmin")
    public boolean isAdmin(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        boolean isAdmin = false;
        if(user != null) {
            isAdmin = user.isAdmin();
        }
        return isAdmin;
    }

    @PostMapping("/addRole")
    public Iterable<Role> addRole(@RequestBody Role role) {
        return userService.addRole(role);
    }
}
