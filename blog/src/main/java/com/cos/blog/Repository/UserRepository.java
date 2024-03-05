package com.cos.blog.Repository;


import com.cos.blog.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUsername(String username);
    //JPA 네이밍 전략?
//    Users findByUsernameAndPassword(String username, String password);
}
