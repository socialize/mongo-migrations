package com.sharethis.mongodb.exception;

import java.text.MessageFormat;

public class PropertyNotFoundException extends Exception {
    private final static String message = "--- Property {0} is missed in properties file. Add it and try again.";

    public PropertyNotFoundException(String propertyName) {
        super(MessageFormat.format(message, propertyName));
    }
}
