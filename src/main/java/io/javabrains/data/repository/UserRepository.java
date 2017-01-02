package io.javabrains.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.javabrains.data.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
	public User findByEmail(String email);
	

}