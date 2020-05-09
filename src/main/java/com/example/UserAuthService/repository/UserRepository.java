package com.example.UserAuthService.repository;

import com.example.UserAuthService.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
