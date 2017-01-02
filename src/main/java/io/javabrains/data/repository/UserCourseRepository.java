package io.javabrains.data.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import io.javabrains.data.entity.UserCourse;

public interface UserCourseRepository extends PagingAndSortingRepository<UserCourse, String> {
	public UserCourse findByEmailAndCourseCode(String email, String courseCode);
	public List<UserCourse> findByEmail(String email, Pageable pageable);
	public List<UserCourse> findByUserId(String userId, Pageable page);
	public UserCourse findByUserIdAndCourseCode(String userId, String courseCode);
	
	
}