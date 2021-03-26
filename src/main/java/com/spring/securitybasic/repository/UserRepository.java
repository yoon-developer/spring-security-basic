package com.spring.securitybasic.repository;

import com.spring.securitybasic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByAccount(String account);

}
