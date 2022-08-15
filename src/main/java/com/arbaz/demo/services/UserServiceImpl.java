package com.arbaz.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arbaz.demo.models.User;
import com.arbaz.demo.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepositroy;
	
	@Override
	public User addUser(User user) {
		User resultUser=userRepositroy.save(user);
		return resultUser;
	}

	@Override
	public List<User> getAllCustomer(String roles) {
		List<User> user=userRepositroy.findByRoles(roles);
		return user;
	}

	@Override
	public void deleteUserById(int id) {
		User user=userRepositroy.findById(id).get();
		userRepositroy.delete(user);
	}

	@Override
	public User getCustomerById(int id) {
		User user=userRepositroy.findById(id).get();
		return user;
	}

}
