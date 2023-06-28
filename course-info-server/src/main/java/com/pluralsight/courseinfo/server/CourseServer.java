package com.pluralsight.courseinfo.server;

import com.pluralsight.courseinfo.repository.CourseRepository;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.logging.LogManager;

public class CourseServer {

    static {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }
    private static Logger LOG = LoggerFactory.getLogger(CourseServer.class);

    public static void main(String[] args) {
        String databaseFileName = loadDatabaseFileName("course-info.database");
        LOG.info("Starting HTTP Server with database {}", databaseFileName);
        CourseRepository courseRepository = CourseRepository.openCourseRepository(databaseFileName);
        ResourceConfig config = new ResourceConfig().register(new CourseResource((courseRepository)));

        GrizzlyHttpServerFactory.createHttpServer(URI.create(loadDatabaseFileName("course-info.server")), config);
    }

    private static String loadDatabaseFileName(String propertyName) {
        try (InputStream propertiesStream = CourseServer.class.getResourceAsStream("/server.properties")) {
            Properties properties = new Properties();
            properties.load(propertiesStream);
            return properties.getProperty(propertyName);
        } catch (IOException ioException) {
            throw new IllegalArgumentException("Could not load database file name.");
        }
    }
}
