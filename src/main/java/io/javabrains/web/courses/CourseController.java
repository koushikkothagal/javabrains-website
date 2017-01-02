package io.javabrains.web.courses;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.javabrains.data.entity.Course;
import io.javabrains.data.entity.Topic;
import io.javabrains.data.entity.User;
import io.javabrains.data.entity.UserCourse;
import io.javabrains.data.service.CourseDataService;
import io.javabrains.data.service.UserService;

@Controller
public class CourseController {

	@Autowired
	CourseDataService courseDataService;
	
	@Autowired
	UserService userService;

	@RequestMapping("/topics/{topicCode}")
	public String allCoursesForTopic(@PathVariable String topicCode, Model model) {
		Topic topic = courseDataService.findTopicByCode(topicCode);
		if (topic == null) {
			return "home";
		}
		model.addAttribute("courses", courseDataService.findCourseByTopicCode(topicCode));
		model.addAttribute("topicName", topic.getName());
		model.addAttribute("title", topic.getName() + " Courses - Java Brains");
		return "courses";
	}
	
	@RequestMapping("/courses")
	public String allCourses(Model model) {
		model.addAttribute("courses", courseDataService.findAllCourses());
		model.addAttribute("title", "All Courses - Java Brains");
		return "all-courses";
	}
	
	@RequestMapping("/courses/{courseCode}")
	public String courseDetail(@PathVariable String courseCode, Model model, @AuthenticationPrincipal User activeUser) {
		Course course = courseDataService.findCourseByCode(courseCode);
		if (activeUser != null) {
			UserCourse userCourse = userService.findUserCourseByUser(activeUser.getId(), courseCode);
			if (userCourse != null) {
				model.addAttribute("completedLessons", userCourse.getLessons());
				if (userCourse.getCompletedDate() != null) {
					model.addAttribute("completedDate", userCourse.getCompletedDate());
				}
				Set<String> userLessons = userCourse.getLessons();
				model.addAttribute("latestLesson", userCourse.getLatestLesson());
				/*
				course.getUnits().forEach(unit -> {
					unit.getLessons().forEach(lesson -> {
						
						if (userLessons.contains(lesson.getPermalinkName())) {
							lesson.setType("completed");
						}
					});
					
					
				});
				*/
			}
		}
		
		
		model.addAttribute("course", course);
		model.addAttribute("title", course.getName() + " - Java Brains");
		
		return "course";
	}

}
