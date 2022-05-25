package com.tectoro.Service;

import java.util.Optional;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.tectoro.Model.User;
import com.tectoro.Repo.UserRepository;

@Service
public class UserServiceImpl implements UserService,UserDetailsService
{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public User saveUser(User user) {
		
		// encode password
		String encode = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encode);
		return repository.save(user);
	
		
		
		
	}
	//get user by username
	@Override
	public Optional<User> findByName(String username) {
		
		return repository.findByName(username);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> opt = findByName(username);
		if(opt.isEmpty())
		{
			throw new UsernameNotFoundException("user not found");
		}
		
		//READ USER(FROM DB)
		User user = opt.get();
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),user.getRoles().stream()
				.map(roles->new SimpleGrantedAuthority(roles)).collect(Collectors.toList()));
	}

}
