package com.sharethis.mongodb.exception;

import java.net.UnknownHostException;
import java.text.MessageFormat;

public class MongoDBConnectionException extends UnknownHostException {
    private final static String message = "--- Cannot connect to mongodb server on host {} port {}";

    public MongoDBConnectionException(String port, Integer host) {
        super(MessageFormat.format(message, port, host));
    }
}
