package io.javabrains.web.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.javabrains.data.entity.ContactUsMessage;
import io.javabrains.data.entity.User;
import io.javabrains.data.service.UserService;



@Controller
public class MessageController {
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(value="/contactus", method=RequestMethod.POST)
	public @ResponseBody String submitMessage(@AuthenticationPrincipal User activeUser, @RequestBody ContactUsMessage message) {
		if (userService.saveContactUsMessage(message) != null) {
			return "success";
		}
		return "error";
	}
	
	
	
}
