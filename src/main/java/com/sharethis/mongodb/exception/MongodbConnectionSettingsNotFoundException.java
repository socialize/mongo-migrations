package com.sharethis.mongodb.exception;

import java.io.FileNotFoundException;
import java.text.MessageFormat;

public class MongoDBConnectionSettingsNotFoundException extends FileNotFoundException {
    private final static String message = "--- Application can not find mongo connection settings file : {0}";

    public MongoDBConnectionSettingsNotFoundException(String scriptPath) {
        super(MessageFormat.format(message, scriptPath));
    }
}
