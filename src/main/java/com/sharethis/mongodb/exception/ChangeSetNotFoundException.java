package com.sharethis.mongodb.exception;

import java.io.FileNotFoundException;
import java.text.MessageFormat;

public class ChangeSetNotFoundException extends FileNotFoundException {
    private static final String MESSAGE = "Application can not find 'scripts' folder with mongodb migration scripts : {0}";

    public ChangeSetNotFoundException(String scriptFolderPath) {
        super(MessageFormat.format(MESSAGE, scriptFolderPath));
    }
}
