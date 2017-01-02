package io.javabrains.data.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "UserCourses")
@TypeAlias("uc")
public class UserCourse {

	@Id	
	private String id;
	private String userId;
	private String courseCode;
	private String email;
	private Set<String> lessons;
	private LocalDateTime completedDate;
	private String latestLesson;
	private LocalDateTime updatedDate;
	private int points;
	
	public UserCourse() {
		lessons = new HashSet<>();
		points = 0;
	}
	
	public UserCourse(String courseCode, String userId, String email) {
		lessons = new HashSet<>();
		this.courseCode = courseCode;
		this.userId = userId;
		this.email = email;
		updatedDate = LocalDateTime.now();
		points = 0;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<String> getLessons() {
		return lessons;
	}
	public void setLessons(Set<String> lessons) {
		this.lessons = lessons;
	}
	public LocalDateTime getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(LocalDateTime completedDate) {
		this.completedDate = completedDate;
	}
	public String getLatestLesson() {
		return latestLesson;
	}
	public void setLatestLesson(String latestLesson) {
		this.latestLesson = latestLesson;
	}
	public void addLesson(String lesson, String type) {
		if (!lessons.contains(lesson)) {
			lessons.add(lesson);
			if ("video".equals(type)) {
				points += 10;
			}
			else if ("quiz".equals(type)) {
				points += 50;
			}
		}
		latestLesson = lesson;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
	
	
	
	
}
