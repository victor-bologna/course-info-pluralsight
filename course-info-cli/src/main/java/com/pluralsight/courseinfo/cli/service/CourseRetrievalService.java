package com.pluralsight.courseinfo.cli.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class CourseRetrievalService {

    private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpClient CLIENT = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public List<PluralsightCourse> getCoursesFor(String authorName) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(PS_URI.formatted(authorName)))
                .GET()
                .build();
        try {
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return switch (response.statusCode()) {
                case HttpURLConnection.HTTP_OK -> getPluralsightCourses(response);
                case HttpURLConnection.HTTP_NOT_FOUND -> Collections.emptyList();
                default ->
                        throw new RuntimeException("Pluralsight API call failed with status code " + response.statusCode());
            };
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException("Could not call Pluralsight API", exception);
        }
    }

    private static List<PluralsightCourse> getPluralsightCourses(HttpResponse<String> response) throws JsonProcessingException {
        JavaType returnType = OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, PluralsightCourse.class);
        return OBJECT_MAPPER.readValue(response.body(), returnType);
    }
}
