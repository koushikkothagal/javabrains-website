package io.javabrains.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.javabrains.data.entity.Course;

public interface CourseRepository extends MongoRepository<Course, String> {
	public Course findByCode(String code);
	public List<Course> findByCodeIn(List<String> code);
	public List<Course> findByTopicCode(String topicCode);

}