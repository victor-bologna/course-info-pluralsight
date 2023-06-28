package com.pluralsight.courseinfo.domain;

import java.util.Optional;

public record Course(String id, String name, long length, String url, Optional<String> notes) {

    public Course {
        filled(id);
        filled(name);
        filled(url);
        notes.ifPresent(Course::filled);
    }

    public static void filled(String stringValue) {
        if(stringValue == null || stringValue.isBlank()) {
            throw new IllegalArgumentException("Field is null.");
        }
    }
}
