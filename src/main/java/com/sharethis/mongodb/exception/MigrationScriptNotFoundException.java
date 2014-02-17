package com.sharethis.mongodb.exception;

import java.io.FileNotFoundException;
import java.text.MessageFormat;

public class MigrationScriptNotFoundException extends FileNotFoundException {
    private final static String message = "Mongo migration script {0} wa not found.";

    public MigrationScriptNotFoundException(String scriptPath) {
        super(MessageFormat.format(message, scriptPath));
    }
}
