package io.javabrains.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.javabrains.data.entity.RememberMeToken;

public interface RememberMeTokenRepository extends MongoRepository<RememberMeToken, String> {
    RememberMeToken findBySeries(String series);
    List<RememberMeToken> findByUsername(String username);
}