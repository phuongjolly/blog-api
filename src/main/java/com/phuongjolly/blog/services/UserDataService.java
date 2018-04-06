package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.Role;
import com.phuongjolly.blog.models.User;
//import com.phuongjolly.blog.models.UserPrincipal;
import com.phuongjolly.blog.repository.RoleRepository;
import com.phuongjolly.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserDataService implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserDataService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    UserDataService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
/*
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        return new UserPrincipal(user);
    }
*/
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
            userRepository.save(newUser);
            return true;
        }
        return false;
    }

    @Override
    public User login(User loginInfo) {
        User user = userRepository.findByEmailAndPassword(loginInfo.getEmail(), loginInfo.getPassword());
        return user;
    }

    @Override
    public User getCurrentUserLogin(HttpSession session) {
        User data = (User) session.getAttribute("currentUser");

        if(data != null){
            Long currentUserId = Long.valueOf(data.getId());
            Optional<User> users = userRepository.findById(currentUserId);
            return users.get();
        }
        else {
            logger.info("data get from session is null");
        }
        return null;
    }
}
