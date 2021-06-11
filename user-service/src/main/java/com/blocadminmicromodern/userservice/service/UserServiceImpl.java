package com.blocadminmicromodern.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocadminmicromodern.userservice.dto.UserDTO;
import com.blocadminmicromodern.userservice.entity.User;
import com.blocadminmicromodern.userservice.repository.UserRepository;
import com.datastax.driver.core.utils.UUIDs;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userDAO;

	@Autowired
	public UserServiceImpl(UserRepository userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional
	@Override
	public List<UserDTO> getUsers() {
		List<UserDTO> users = new ArrayList<>();

		List<User> userEntities = userDAO.findAll();
		for (User userEntity : userEntities) {
			UserDTO userDTO = getDTO(userEntity);
			users.add(userDTO);
		}
		return users;
	}

	private UserDTO getDTO(User userEntity) {
		if (userEntity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		UserDTO entityDTO = new UserDTO();
		entityDTO.setAppartmentNr(userEntity.getAppartmentNr());
		entityDTO.setBuildingNr(userEntity.getBuildingNr());
		entityDTO.setDetails(userEntity.getDetails());
		entityDTO.setFirstName(userEntity.getFirstName());
		entityDTO.setLastName(userEntity.getLastName());
		entityDTO.setUsername(userEntity.getUsername());

		if (userEntity.getPassword() != null)
			entityDTO.setPassword(userEntity.getPassword());
		entityDTO.setUserType(userEntity.getUserType());
		entityDTO.setUuid(userEntity.getUuid());
		return entityDTO;
	}

	private User getEntity(UserDTO userEntityDTO) {
		if (userEntityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		User userEntity = new User();
		userEntity.setAppartmentNr(userEntityDTO.getAppartmentNr());
		userEntity.setBuildingNr(userEntityDTO.getBuildingNr());

		if (userEntityDTO.getDetails() != null)
			userEntity.setDetails(userEntityDTO.getDetails());

		if (userEntityDTO.getPassword() != null)
			userEntity.setPassword(userEntityDTO.getPassword());

		userEntity.setFirstName(userEntityDTO.getFirstName());
		userEntity.setLastName(userEntityDTO.getLastName());

		if (userEntityDTO.getUsername() != null)
			userEntity.setUsername(userEntityDTO.getUsername());

		userEntity.setUserType(userEntityDTO.getUserType());

		if (userEntityDTO.getUuid() != null)
			userEntity.setUuid(userEntityDTO.getUuid());

		return userEntity;
	}

	@Override
	public void saveUser(UserDTO userDTO) {
		if (userDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		User user = new User();
		user = getEntity(userDTO);
		if (user.getUuid() == null)
			user.setUuid(UUIDs.timeBased());
		if (user.getUsername() == null)
			user.setUsername(user.getLastName().concat("_").concat(user.getFirstName()));
		userDAO.save(user);
	}

	@Override
	public boolean deleteUser(UUID userUUID) {
		if (userUUID == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		userDAO.deleteById(userUUID);
		boolean exists = userDAO.existsById(userUUID);
		return exists;
	}

	@Override
	public UserDTO getUser(UUID userUUID) {
		if (userUUID == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<User> userEntity = userDAO.findById(userUUID);
		UserDTO userEntityDTO = getDTO(userEntity.get());
		return userEntityDTO;
	}
}
