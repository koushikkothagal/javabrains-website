package io.javabrains.web.blog;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.javabrains.data.entity.BlogEntry;
import io.javabrains.data.entity.Topic;
import io.javabrains.data.service.BlogDataService;
import io.javabrains.data.service.CourseDataService;

@Controller
public class BlogController {

	@Autowired
	BlogDataService blogDataService;
	
	
	@RequestMapping("/blog")
	public String home(Model model) {
		//model.addAttribute("topics", courseDataService.findAllTopics());
		//model.addAttribute("title", "Topics - Java Brains");
		/*
		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setPermalink("Write-your-first-Lambda-expression-in-five-minutes");
		blogEntry.setTitle("Write your first Lambda expression in five minutes");
		blogEntry.setCreatedAt(LocalDateTime.now());
		blogEntry.setTags("java", "java 8", "lambda", "core java");
		blogEntry.setContent("<h3>Writing your first Lambda</h3><p>Test paragraph here</p>");
		
		blogDataService.addBlogPost(blogEntry);
		*/
		model.addAttribute("blogEntries", blogDataService.findAllBlogEntries());
		
		
		return "blog-home";
	}
	
	@RequestMapping("/blog/{permalink}")
	public String allCoursesForTopic(@PathVariable String permalink, Model model) {
		BlogEntry blogEntry = blogDataService.findByPermalink(permalink);
		if (blogEntry == null) {
			return "blog-home";
		}
		model.addAttribute("blogEntry", blogEntry);
		model.addAttribute("title", blogEntry.getTitle() + " - Java Brains");
		return "blog-entry";
	}
	
	
}







