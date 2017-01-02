package io.javabrains.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import io.javabrains.data.entity.BlogEntry;
import io.javabrains.data.repository.BlogEntryRepository;

@Service
public class BlogDataService {
	
	@Autowired
	BlogEntryRepository blogEntryRepo;
	
	
	
	public BlogEntry findByPermalink(String permalink) {
		// List<BlogEntry> findAll = blogEntryRepo.findAll(new Sort("createdAt"));
		return blogEntryRepo.findByPermalink(permalink);
	}
	
	public void addBlogPost(BlogEntry blogEntry) {
		blogEntryRepo.save(blogEntry);
	}
	
	public List<BlogEntry> findAllBlogEntries() {
		List<BlogEntry> entries = new ArrayList<>();
		blogEntryRepo.findAll(new Sort(Direction.DESC, "createdAt"))
				.forEach(entries::add);
		return entries;
	}
	

}
