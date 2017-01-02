package io.javabrains.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.javabrains.data.entity.Course;
import io.javabrains.data.entity.Topic;

public interface TopicRepository extends MongoRepository<Topic, String> {
	public Topic findByCode(String code);
	public List<Topic> findByCodeIn(List<String> code);

}