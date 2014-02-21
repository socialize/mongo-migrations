package com.sharethis.mongodb.exception;

import java.text.MessageFormat;

public class PropertyNotFoundException extends Exception {
    private static final String MESSAGE = "Property {0} is missed in properties file. Add it and try again.";

    public PropertyNotFoundException(String propertyName) {
        super(MessageFormat.format(MESSAGE, propertyName));
    }
}
