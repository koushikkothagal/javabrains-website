package io.javabrains.web.login;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.javabrains.data.entity.User;
import io.javabrains.data.service.EmailService;
import io.javabrains.data.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	UserService userService;

	// @Autowired
	BCryptPasswordEncoder bcryptEncoder;
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfilePage(@AuthenticationPrincipal User activeUser, @ModelAttribute User user) {
		user.setEmail(activeUser.getEmail());
		user.setFullName(activeUser.getFullName());
		user.setPassword("");
		user.setEmailVerified(activeUser.isEmailVerified());
		return "profile";
	}
		
	
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String editProfile(@AuthenticationPrincipal User activeUser, @Valid User user, BindingResult result, RedirectAttributes redirect, Model model) {
		if (activeUser == null) {
			// Security lapse! This person isn't authenticated and shouldn't be doing this POST.
			return "redirect:/";
		}
		
		if (result.hasErrors()) {
			model.addAttribute("errorMessage", "Please fill in all the form fields.");
			return "profile";
		}
		// Check if the user has updated their email
		if (!activeUser.getEmail().equalsIgnoreCase(user.getEmail())) {
			// Yes? then check if the email is taken already
			User existingUser = userService.findByEmail(user.getEmail());
			if (existingUser != null) {
				model.addAttribute("errorMessage", "Unable to update your profile. Please try with a different email ID.");
				return "profile";
			}
			// Legit new email. 
			activeUser.setEmail(user.getEmail().toLowerCase());
			activeUser.setEmailVerified(false);
			
		}
		activeUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		activeUser.setFullName(user.getFullName());
		User userToSave = new User(activeUser);
		user = userService.saveUser(userToSave);
		if (!user.isEmailVerified()) {
			userService.generateAndSendEmailVerificationEmail(user);
		}
		
		redirect.addFlashAttribute("globalMessage", "Profile updated!");
		
		return "redirect:/profile";
	}
}
