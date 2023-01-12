package com.auth.reposetory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
