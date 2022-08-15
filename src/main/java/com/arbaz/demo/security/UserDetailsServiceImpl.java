package com.arbaz.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.arbaz.demo.models.User;
import com.arbaz.demo.repositories.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepositroy;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepositroy.getUserByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("Could not found exception !!");
		}
		
		CustomerUserDetails customerUserDetails=new CustomerUserDetails(user);
		
		return customerUserDetails;
	}

}
