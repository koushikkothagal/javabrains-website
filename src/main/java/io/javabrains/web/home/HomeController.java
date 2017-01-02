package io.javabrains.web.home;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auth0.Auth0User;
import com.auth0.SessionUtils;

import io.javabrains.data.entity.Course;
import io.javabrains.data.entity.Lesson;
import io.javabrains.data.entity.User;
import io.javabrains.data.entity.UserCourse;
import io.javabrains.data.service.CourseDataService;
import io.javabrains.data.service.UserService;

@Controller
public class HomeController {

	@Autowired
	CourseDataService courseDataService;
	
	@Autowired
	UserService userService;
	
	@Value("${app.home.topCourses}")
	private String topCoursesProperty;
	
	@Value("${app.home.topTopics}")
	private String topTopicsProperty;
	
	private List<String> topTopics, topCourses;

	@PostConstruct
	public void init() {
		topTopics = Arrays.asList(topTopicsProperty.split(","));
		topCourses = Arrays.asList(topCoursesProperty.split(","));
	}
	
	
	@RequestMapping("/")
	public String home(Model model, @AuthenticationPrincipal User activeUser, @ModelAttribute User user, final HttpServletRequest req, final Principal principal) {
		if (principal != null) {
			System.out.println(principal.getName());
		}
		final Auth0User authUser = SessionUtils.getAuth0User(req);
		if (authUser != null) {
			List<UserCourse> userCourses = userService.findUserCoursesByUser(authUser.getEmail());
			model.addAttribute("userCourses", userCourses);
			if (userCourses.isEmpty()) {
				model.addAttribute("courses", courseDataService.findCourseByCodeIn(topCourses));
			}
			else {
				model.addAttribute("completedLessons", userCourses.get(0).getLessons());
				List<String> courses = new ArrayList<>();
				userCourses.forEach(userCourse -> courses.add(userCourse.getCourseCode()));
				model.addAttribute("courses", courseDataService.findCourseByCodeIn(courses));
				
				Course latestCourse = courseDataService.findCourseByCode(courses.get(0));
				model.addAttribute("latestCourse", latestCourse);
				model.addAttribute("topicCode", latestCourse.getTopicCode());
				Lesson latestLesson = new Lesson(userCourses.get(0).getLatestLesson());
				for (int i = 0; i < latestCourse.getUnits().size(); i++) {
					io.javabrains.data.entity.Unit unit = latestCourse.getUnits().get(i);
					List<Lesson> unitLessons = unit.getLessons();
					int lessonIndex = unitLessons.indexOf(latestLesson);
					if (lessonIndex != -1) {
						model.addAttribute("latestUnit", unit);
						model.addAttribute("latestUnitNumber", i + 1);
						int numberOfRemainingLessonsInUnit = Math.min(lessonIndex + 3, unitLessons.size());
						model.addAttribute("remainingLessons", unitLessons.subList(lessonIndex, numberOfRemainingLessonsInUnit));
					}
				}
				
				model.addAttribute("latestCourse", latestCourse);
				model.addAttribute("latestLesson", latestLesson);
			}
			model.addAttribute("title", "Dashboard");
			return "dashboard";
		}
		model.addAttribute("topCourses", courseDataService.findCourseByCodeIn(topCourses));
		model.addAttribute("topTopics", courseDataService.findTopicByCodeIn(topTopics));
		model.addAttribute("title", "Java Brains");
		return "home";
	}

	

}