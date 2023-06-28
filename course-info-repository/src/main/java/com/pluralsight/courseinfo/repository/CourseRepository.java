package com.pluralsight.courseinfo.repository;

import com.pluralsight.courseinfo.domain.Course;

import java.util.List;

public interface CourseRepository {
    static CourseRepository openCourseRepository(String databaseFile) {
        return new CourseJdbcRepositoryImpl(databaseFile);
    }

    void saveCourse(Course course);
    List<Course> getAllCourses();
    void addNotes(String id, String notes);
}