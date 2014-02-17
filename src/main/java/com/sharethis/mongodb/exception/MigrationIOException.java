package com.sharethis.mongodb.exception;

import java.io.IOException;

public class MigrationIOException extends IOException {

    public MigrationIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
