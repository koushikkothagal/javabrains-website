package io.javabrains.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.javabrains.data.entity.VerificationToken;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {
	public VerificationToken findByCode(String code);
	

}