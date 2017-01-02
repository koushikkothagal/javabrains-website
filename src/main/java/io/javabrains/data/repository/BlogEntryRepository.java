package io.javabrains.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import io.javabrains.data.entity.BlogEntry;
import io.javabrains.data.entity.Course;

public interface BlogEntryRepository extends PagingAndSortingRepository<BlogEntry, String> {
	public BlogEntry findByPermalink(String permalink);
	

}