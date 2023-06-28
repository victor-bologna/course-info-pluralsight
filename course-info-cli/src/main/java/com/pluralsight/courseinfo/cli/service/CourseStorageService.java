package com.pluralsight.courseinfo.cli.service;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

public class CourseStorageService {
    private static final String PS_BASE_URL = "https://app.pluralsight.com";

    private final CourseRepository courseRepository;

    public CourseStorageService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void storePluralsightCourses(List<PluralsightCourse> pluralsightCourseList) {
        for (PluralsightCourse pluralsightCourse: pluralsightCourseList) {
            Course course = new Course(pluralsightCourse.id(), pluralsightCourse.title(),
                    pluralsightCourse.durationInMinutes(), PS_BASE_URL + pluralsightCourse.contentURL(),
                    Optional.empty());
            courseRepository.saveCourse(course);
        }
    }
}
