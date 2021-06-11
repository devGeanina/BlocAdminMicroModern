package com.blocadminmicromodern.userservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicromodern.userservice.dto.UserDTO;
import com.blocadminmicromodern.userservice.service.UserService;

@RestController("/users")
public class UsersController {

	private final UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<UserDTO> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/users/{uuid}")
	UserDTO findUser(@PathVariable UUID uuid) {
		return userService.getUser(uuid);
	}

	@PostMapping("/users/save")
	public List<UserDTO> saveOrUpdateUser(@RequestBody UserDTO userDTO) {
		userService.saveUser(userDTO);
		List<UserDTO> users = userService.getUsers();
		return users;
	}

	@GetMapping("/users/delete/{uuid}")
	public List<UserDTO> deleteUser(@PathVariable UUID uuid) {
		userService.deleteUser(uuid);
		return userService.getUsers();
	}
}