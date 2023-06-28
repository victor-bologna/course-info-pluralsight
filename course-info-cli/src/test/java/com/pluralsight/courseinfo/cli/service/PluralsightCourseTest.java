package com.pluralsight.courseinfo.cli.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PluralsightCourseTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            00:00:00, 0
            00:05:37, 5
            01:07:53, 67
            """)
    void durationInMinutes(String input, long expected) {
        PluralsightCourse pluralsightCourse =  new PluralsightCourse("id", "Test course", input,
                "http://google.com/", false, Date.from(Instant.now()));

        assertEquals(expected, pluralsightCourse.durationInMinutes());
    }
}