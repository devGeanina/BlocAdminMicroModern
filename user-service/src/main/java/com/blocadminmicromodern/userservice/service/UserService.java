package com.blocadminmicromodern.userservice.service;

import java.util.List;
import java.util.UUID;

import com.blocadminmicromodern.userservice.dto.UserDTO;

public interface UserService {

	public abstract List<UserDTO> getUsers();

	public abstract void saveUser(UserDTO userDTO);

	public abstract boolean deleteUser(UUID userUuid);

	public abstract UserDTO getUser(UUID userUuid);
}
