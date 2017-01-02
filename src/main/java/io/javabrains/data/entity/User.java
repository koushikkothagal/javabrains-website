package io.javabrains.data.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "_User")
public class User implements Serializable{

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -4756434823198388327L;
	@Id
	@Field(value="_id")
	private String id;
	@NotEmpty
	private String email;
	@NotEmpty
	private String fullName;
	@Field(value="username")
	private String user;
	@NotEmpty @Field(value="_hashed_password")
	private String password;
	private boolean emailVerified;
	@Transient
	private String token;
	@Field(value="p")
	private String p;
	
	
	public User() {}

	public User(User user) {
        this.id = user.id;
        this.fullName = user.fullName;
        this.email = user.email;
        this.password = user.password;
        this.user = user.user;
        this.emailVerified = user.emailVerified;
        this.token = user.token;
        this.p = user.p;
    }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	
	
}
