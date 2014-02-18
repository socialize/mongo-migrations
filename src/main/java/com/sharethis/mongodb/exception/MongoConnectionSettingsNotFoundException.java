package com.sharethis.mongodb.exception;

import java.io.FileNotFoundException;
import java.text.MessageFormat;

public class MongoConnectionSettingsNotFoundException extends FileNotFoundException {
    private static final String MESSAGE = "Application can not find mongo connection settings file : {0}";

    public MongoConnectionSettingsNotFoundException(String scriptPath) {
        super(MessageFormat.format(MESSAGE, scriptPath));
    }
}
