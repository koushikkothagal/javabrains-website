package io.javabrains.data.service;

import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import io.javabrains.data.entity.User;
import io.javabrains.data.entity.VerificationToken;

@Service
public class EmailService {

	private static final String MAILGUN_API_BASE_URL = "https://api.mailgun.net/v3/javabrains.io";
	
	@Value("${mailgun.api.key}")
	private String mailgunApiKey;
	
	private Client client;
	private WebResource webResource;
	
	@PostConstruct
	public void init() {
		client = Client.create();
	    client.addFilter(new HTTPBasicAuthFilter("api", mailgunApiKey));
	    webResource = client.resource(MAILGUN_API_BASE_URL + "/messages");
	}
	
	public void sendSignupEmail(User user, VerificationToken token) {
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "admin@javabrains.io");
        formData.add("to", user.getEmail());
        formData.add("subject", "Your Java Brains account");
        formData.add("html", "<h1>Welcome to Java Brains</h1><h2>Thanks for signing up!</h2> <p>You now have a Java Brains account that lets you track courses and earn points!</p><p>Click this link to confirm your email and activate your account: <a href='https://javabrains.io/verify/" + token.getCode() + "'>Click here</a> ");

        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
        String output = clientResponse.getEntity(String.class);
	}
	
	public void sendEmailVerificationEmail(User user, VerificationToken token) {
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "admin@javabrains.io");
        formData.add("to", user.getEmail());
        formData.add("subject", "Your Java Brains account verification");
        formData.add("html", "<h1>Verify your email</h1><p>You are a click away from a verified Java Brains account!</p><p>Click this link to confirm your email and activate your account: <a href='https://javabrains.io/verify/" + token.getCode() + "'>Click here</a> ");

        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
        String output = clientResponse.getEntity(String.class);
	}

	public void sendResetPasswordEmail(User user, VerificationToken token) {
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "admin@javabrains.io");
        formData.add("to", user.getEmail());
        formData.add("subject", "Your Java Brains password reset request on " + token.getCreatedAt().format(DateTimeFormatter.ofPattern("MMM d")));
        formData.add("html", "<h1>Your Java Brains password reset request</h1><p>I've just received a request to reset your password. If that was you, click the link below to reset your password. You have 24 hours to click and reset your password, after which this link expires.</p><p>If you didn't make this request, please ignore this email.</p><p>Click this link to reset your password: <a href='https://javabrains.io/reset/" + token.getCode() + "'>Click here</a> ");

        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
        String output = clientResponse.getEntity(String.class);
		
	}

}
