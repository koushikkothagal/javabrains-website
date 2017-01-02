package io.javabrains.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.javabrains.data.entity.Course;
import io.javabrains.data.entity.LessonDetail;
import io.javabrains.data.entity.Topic;
import io.javabrains.data.repository.CourseRepository;
import io.javabrains.data.repository.LessonDetailRepository;
import io.javabrains.data.repository.TopicRepository;

@Service
public class CourseDataService {
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	TopicRepository topicRepo;
	
	@Autowired
	LessonDetailRepository lessonDetailRepo;
	
	@Cacheable("topCourses")
	public List<Course> findCourseByCodeIn(List<String> courseCodes) {
		List<Course> courseList = courseRepo.findByCodeIn(courseCodes);
		populateTopicsForCourseList(courseList);
		return courseList;
	}
	
	// @Cacheable("courses")
	public Course findCourseByCode(String courseCode) {
		Course course = courseRepo.findByCode(courseCode);
		course.setTopic(topicRepo.findByCode(course.getTopicCode()));
		return course;
	}
	
	@Cacheable("topTopics")
	public List<Topic> findTopicByCodeIn(List<String> topicCodes) {
		return topicRepo.findByCodeIn(topicCodes);
	}
	
	@Cacheable("topics")
	public Topic findTopicByCode(String topicCode) {
		return topicRepo.findByCode(topicCode);
	}

	@Cacheable("allTopics")
	public List<Topic> findAllTopics() {
		return topicRepo.findAll();
	}
	
	// @Cacheable("courseByTopic")
	public List<Course>  findCourseByTopicCode(String topicCode) {
		List<Course> courseList = courseRepo.findByTopicCode(topicCode);
		populateTopicsForCourseList(courseList);
		return courseList;
	}
	
	public LessonDetail findLessonByCourseAndLesson(String courseCode, String permalinkName) {
		return lessonDetailRepo.findByCourseCodeAndPermalinkName(courseCode, permalinkName);		
	}
	
	
	private void populateTopicsForCourseList(List<Course> courseList) {
		courseList.forEach((course) -> {
			course.setTopic(topicRepo.findByCode(course.getTopicCode()));
		});
	}

	// @Cacheable("allCourses")
	public List<Course> findAllCourses() {
		List<Course> courseList = courseRepo.findAll();
		populateTopicsForCourseList(courseList);
		return courseList;
	}

}
