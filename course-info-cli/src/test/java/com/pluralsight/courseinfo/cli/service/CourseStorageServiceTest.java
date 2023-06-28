package com.pluralsight.courseinfo.cli.service;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseStorageServiceTest {

    @Test
    void storePluralsightCourses() {
        CourseRepository repository = new InMemoryCourseRepository();
        CourseStorageService courseStorageService = new CourseStorageService(repository);

        PluralsightCourse pluralsightCourse = new PluralsightCourse("1", "Title 1", "01:40:00",
                "/url-1", false, Date.from(Instant.now()));
        courseStorageService.storePluralsightCourses(Collections.singletonList(pluralsightCourse));

        Course expected = new Course("1", "Title 1", 100, "https://app.pluralsight.com/url-1",
                Optional.empty());

        assertEquals(Collections.singletonList(expected), repository.getAllCourses());
    }

    static class InMemoryCourseRepository implements CourseRepository {
        private final List<Course> courseList = new ArrayList<>();

        @Override
        public void saveCourse(Course course) {
            courseList.add(course);
        }

        @Override
        public List<Course> getAllCourses() {
            return courseList;
        }

        @Override
        public void addNotes(String id, String notes) {
            throw new UnsupportedOperationException();
        }
    }
}