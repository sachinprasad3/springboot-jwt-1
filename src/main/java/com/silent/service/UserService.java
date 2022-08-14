package com.silent.service;

import java.util.Optional;

import com.silent.model.User;

public interface UserService {
	
	public User insert(User user);
	
	public Optional<User> findByUsername(String username);

}
