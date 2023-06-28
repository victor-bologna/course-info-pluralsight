package com.pluralsight.courseinfo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, Test course, https://google.com
            2, Abacadabra course, https://outlook.com""")
    void courseInfoFilled(String id, String name, String url) {
        Course course = new Course(id, name, 60, url, Optional.empty());

        assertNotEquals(null, course.id());
        assertNotEquals(null, course.name());
        assertNotEquals(null, course.url());
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            , Test course, https://google.com
            1, , https://google.com
            1, , """)
    void courseInfoNotFilled(String id, String name, String url) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Course(id, name, 60, url, Optional.of("")), "Field is null.");
    }

    @Test
    void courseInfoNullFilled() {
        assertThrows(IllegalArgumentException.class, () -> new Course(null, "Test Course", 60,
                "https://google.com/", Optional.empty()), "Field is null");
    }
}