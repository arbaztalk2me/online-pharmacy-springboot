package com.arbaz.demo.services;

import java.util.List;

import com.arbaz.demo.models.User;

public interface UserService {
	User addUser(User user);
	List<User> getAllCustomer(String roles);
	void deleteUserById(int id);
	User getCustomerById(int id);
}
