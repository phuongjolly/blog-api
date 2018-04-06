package com.phuongjolly.blog.repository;

import com.phuongjolly.blog.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
