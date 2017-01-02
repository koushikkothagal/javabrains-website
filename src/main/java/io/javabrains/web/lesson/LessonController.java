package io.javabrains.web.lesson;

import java.time.LocalDateTime;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.javabrains.data.entity.Course;
import io.javabrains.data.entity.Lesson;
import io.javabrains.data.entity.LessonDetail;
import io.javabrains.data.entity.User;
import io.javabrains.data.entity.UserCourse;
import io.javabrains.data.service.CourseDataService;
import io.javabrains.data.service.UserService;

@Controller
public class LessonController {
	

	@Autowired
	CourseDataService courseDataService;
	
	@Autowired
	UserService userService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/courses/{courseCode}/lessons/{lessonCode}")
	public String showLessonPage(@PathVariable String courseCode, @PathVariable String lessonCode, 
									Model model, @AuthenticationPrincipal User activeUser) {
		
		Course course = courseDataService.findCourseByCode(courseCode);
		LessonDetail lessonDetail = courseDataService.findLessonByCourseAndLesson(courseCode, lessonCode);
		
		if (activeUser != null) {
			UserCourse userCourse = userService.findUserCourseByUser(activeUser.getId(), courseCode);
			if (userCourse == null) {
				userCourse = new UserCourse(courseCode, activeUser.getId(), activeUser.getEmail());
			}
			model.addAttribute("completedLessons", userCourse.getLessons());
			model.addAttribute("pointsEarned", userCourse.getPoints());
			if ("video".equals(lessonDetail.getType())) {
				userCourse.addLesson(lessonCode, lessonDetail.getType());
				userCourse.setUpdatedDate(LocalDateTime.now());
				
				if (userCourse.getLessons().size() >= course.getTotalLessons()) {
					userCourse.setCompletedDate(LocalDateTime.now());
				}
				userService.saveUserCourse(userCourse);
				log.error("LessonController: Getting lesson " + lessonDetail.getPermalinkName() + " for user " + userCourse.getEmail());
			}
		}
		model.addAttribute("course", course);
		model.addAttribute("thisUnit", course.getUnits().get(lessonDetail.getUnitNo() - 1));
		model.addAttribute("lesson", lessonDetail);
		model.addAttribute("evaluator", new Evaluator());
		return lessonDetail.getType();
	}
	
	
	@RequestMapping(value="/courses/{courseCode}/lessons/{lessonCode}/submit")
	public @ResponseBody String submitQuizCompletion(@PathVariable String courseCode, @PathVariable String lessonCode, 
									@AuthenticationPrincipal User activeUser) {
		
		Course course = courseDataService.findCourseByCode(courseCode);
		LessonDetail lessonDetail = courseDataService.findLessonByCourseAndLesson(courseCode, lessonCode);
		
		if (activeUser != null) {
			UserCourse userCourse = userService.findUserCourseByUser(activeUser.getId(), courseCode);
			if (userCourse == null) {
				userCourse = new UserCourse(courseCode, activeUser.getId(), activeUser.getEmail());
			}
			
			userCourse.addLesson(lessonCode, lessonDetail.getType());
			userCourse.setUpdatedDate(LocalDateTime.now());
			
			if (userCourse.getLessons().size() >= course.getTotalLessons()) {
				userCourse.setCompletedDate(LocalDateTime.now());
			}
			
			userService.saveUserCourse(userCourse);
		}
		return "success";
	}
	
	
	
	public class Evaluator {
		
		public String evaluateClass(Set<String> completedLessons, Lesson lesson) {
			if (completedLessons != null && completedLessons.contains(lesson.getPermalinkName())) {
				return "fa-check-circle";
			}
			return lesson.getType().equals("quiz") ? "fa-question-circle" : "fa-play-circle";
			
		}
		
		
	}
	
	
	
	

}
