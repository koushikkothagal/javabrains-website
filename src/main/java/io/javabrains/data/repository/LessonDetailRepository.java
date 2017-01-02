package io.javabrains.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.javabrains.data.entity.LessonDetail;

public interface LessonDetailRepository extends MongoRepository<LessonDetail, String> {
	public LessonDetail findByCourseCodeAndPermalinkName(String courseCode, String permalinkName);
	
}