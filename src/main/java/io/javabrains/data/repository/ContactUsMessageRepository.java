package io.javabrains.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.javabrains.data.entity.ContactUsMessage;

public interface ContactUsMessageRepository extends MongoRepository<ContactUsMessage, String> {
	

}