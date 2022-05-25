package com.tectoro.Service;

import java.util.Optional;

import com.tectoro.Model.User;

public interface UserService {
	
	public User saveUser(User user);
	
	Optional<User> findByName(String username);

}
