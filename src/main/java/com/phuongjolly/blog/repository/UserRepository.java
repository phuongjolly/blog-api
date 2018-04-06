package com.phuongjolly.blog.repository;

import com.phuongjolly.blog.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
