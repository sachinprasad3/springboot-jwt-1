package com.silent.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.silent.model.User;
import com.silent.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	//save method
	@Override
	public User insert(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repo.save(user);
	}

	//get by username
	@Override
	public Optional<User> findByUsername(String username) {
		return repo.findByUsername(username);
	}

	/*******************************/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = findByUsername(username); //here User in model class user
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("user not exists");
		}
		
		User u = user.get();
		
		//here returing Spring Security User Object
		return new org.springframework.security.core.userdetails.User(username,
				u.getPassword(),
				u.getRoles().stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList())
				);
	}

}
