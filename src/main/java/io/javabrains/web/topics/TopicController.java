package io.javabrains.web.topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.javabrains.data.service.CourseDataService;

@Controller
public class TopicController {


	@Autowired
	CourseDataService courseDataService;
	
	
	@RequestMapping("/topics")
	public String home(Model model) {
		model.addAttribute("topics", courseDataService.findAllTopics());
		model.addAttribute("title", "Topics - Java Brains");
		return "topics";
	}

}
