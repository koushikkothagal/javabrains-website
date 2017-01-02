package io.javabrains.data.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "VerificationToken")
public class VerificationToken {

	@Id
	private String id;
	private String code;
	@DBRef
	private User user;
	private VerificationToken.TokenType type;
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	

	public VerificationToken() {
		this.code = UUID.randomUUID().toString();
		
	}
	
	
	public VerificationToken(User user, TokenType tokenType) {
		this.code = UUID.randomUUID().toString();
		this.user = user;
		this.type = tokenType;
		this.createdAt = LocalDateTime.now();
		if (TokenType.FORGOT_PASSWORD.equals(tokenType)) {
			this.expiresAt = this.createdAt.plusHours(24);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VerificationToken.TokenType getType() {
		return type;
	}

	public void setType(VerificationToken.TokenType type) {
		this.type = type;
	}
	

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}


	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}


	public enum TokenType {
		REGISTRATION, FORGOT_PASSWORD, VERIFY_EMAIL
	}

}
