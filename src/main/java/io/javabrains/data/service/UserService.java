package io.javabrains.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.javabrains.data.entity.ContactUsMessage;
import io.javabrains.data.entity.User;
import io.javabrains.data.entity.UserCourse;
import io.javabrains.data.entity.VerificationToken;
import io.javabrains.data.entity.VerificationToken.TokenType;
import io.javabrains.data.repository.ContactUsMessageRepository;
import io.javabrains.data.repository.UserCourseRepository;
import io.javabrains.data.repository.UserRepository;
import io.javabrains.data.repository.VerificationTokenRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserCourseRepository ucRepo;
	
	@Autowired
	private CourseDataService courseDataService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerificationTokenRepository tokenRepo;
	
	@Autowired
	private ContactUsMessageRepository contactUsMessageRepository;
	
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public UserCourse findUserCourseByUser(String userId, String courseCode) {
		return ucRepo.findByUserIdAndCourseCode(userId, courseCode);
	}
	
	public UserCourse saveUserCourse(UserCourse userCourse) {
		return ucRepo.save(userCourse);
	}

	public List<UserCourse> findUserCoursesByEmail(String email) {
		Pageable page = new PageRequest(0, 3);
		List<UserCourse> userCourses = ucRepo.findByEmail(email, page);
		userCourses.sort((uc1, uc2) -> uc2.getUpdatedDate().compareTo(uc1.getUpdatedDate()));
		return userCourses;
	}

	public User saveUser(User user) {
		user.setUser(user.getEmail());
		return userRepo.save(user);
	}
	
	public User createUser(User user) {
		user.setUser(user.getEmail());
		VerificationToken token = new VerificationToken(user, TokenType.REGISTRATION);
		user = userRepo.save(user);
		tokenRepo.save(token);
		emailService.sendSignupEmail(user, token);
		return user;
	}

	public List<UserCourse> findUserCoursesByUser(String id) {
		Pageable page = new PageRequest(0, 10);
		List<UserCourse> userCourses = ucRepo.findByUserId(id, page);
		userCourses.sort((uc1, uc2) -> uc2.getUpdatedDate().compareTo(uc1.getUpdatedDate()));
		return userCourses;
	}

	public VerificationToken findVerificationToken(String verificationCode) {
		return tokenRepo.findByCode(verificationCode);
	}

	public void generateAndSendResetPasswordEmail(User user) {
		VerificationToken token = new VerificationToken(user, TokenType.FORGOT_PASSWORD);
		tokenRepo.save(token);
		emailService.sendResetPasswordEmail(user, token);
	}
	
	public void generateAndSendEmailVerificationEmail(User user) {
		VerificationToken token = new VerificationToken(user, TokenType.VERIFY_EMAIL);
		tokenRepo.save(token);
		emailService.sendEmailVerificationEmail(user, token);
	}

	public void deleteToken(VerificationToken token) {
		tokenRepo.delete(token);
		
	}
	
	public ContactUsMessage saveContactUsMessage(ContactUsMessage message) {
		return contactUsMessageRepository.save(message);
	}
	
	

}
