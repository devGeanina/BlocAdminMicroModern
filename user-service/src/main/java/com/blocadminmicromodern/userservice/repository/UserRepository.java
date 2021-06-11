package com.blocadminmicromodern.userservice.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocadminmicromodern.userservice.entity.User;

@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {

	@Query("SELECT u FROM User u WHERE LOWER(username)= ?0 AND LOWER(password)= ?1")
	User findUserByUsernameAndPassword(String username, String password);
}