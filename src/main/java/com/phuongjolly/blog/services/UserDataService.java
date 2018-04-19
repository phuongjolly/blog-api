package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.Role;
import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.models.requests.LoginRequest;
import com.phuongjolly.blog.repository.RoleRepository;
import com.phuongjolly.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Service
@Component
public class UserDataService implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserDataService.class);
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    UserDataService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public boolean register(User newUser) {
        User user = userRepository.findByEmail(newUser.getEmail());
        if(user == null) {
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName(Role.USER));
            newUser.setRoles(roles);

            //String encodePassword = passwordEncoder.encode(newUser.getPassword());
            //newUser.setPassword(encodePassword);

            userRepository.save(newUser);
            return true;
        }
        return false;
    }

    @Override
    public User login(LoginRequest request) {

        //String encodePassword = passwordEncoder.encode(request.getPassword());
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        return user;
    }
}
