package com.phuongjolly.blog.services;
import com.phuongjolly.blog.models.Role;
import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.models.requests.LoginRequest;
import com.phuongjolly.blog.models.requests.RegisterRequest;
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
    public User register(RegisterRequest registerInfo) {
        User user = userRepository.findByEmail(registerInfo.getEmail());
        if(user == null) {
            user = new User();
            user.setName(registerInfo.getName());
            user.setEmail(registerInfo.getEmail());
            user.setPassword(passwordEncoder.encode(registerInfo.getPassword()));

            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName(Role.USER));
            user.setRoles(roles);


            userRepository.save(user);
        }
        return user;
    }

    @Override
    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if(user != null) {
            String password = user.getPassword();
            if(passwordEncoder.matches(request.getPassword(), password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Iterable<Role> addRole(Role role) {
        roleRepository.save(role);
        return roleRepository.findAll();
    }

    @Override
    public User setAdmin(User requestInfo) {
        User user = userRepository.findByEmail(requestInfo.getEmail());
        if(user != null) {
            Role role = roleRepository.findByName(Role.ADMININISTRATOR);
            List<Role> roles = user.getRoles();
            if(roles.isEmpty()) {
                roles = new ArrayList<>();
            }
            roles.add(role);
            user.setRoles(roles);
            return userRepository.save(user);
        }
        return null;
    }
}
