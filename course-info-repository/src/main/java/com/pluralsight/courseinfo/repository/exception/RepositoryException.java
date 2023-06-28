package com.pluralsight.courseinfo.repository.exception;

import java.sql.SQLException;

public class RepositoryException extends RuntimeException {
    public RepositoryException(String message, SQLException sqlException) {
        super(message, sqlException);
    }
}
