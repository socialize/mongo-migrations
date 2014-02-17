package com.sharethis.mongodb.exception;

import java.net.UnknownHostException;
import java.text.MessageFormat;

public class MongoDBConnectionException extends UnknownHostException {
    private static final String MESSAGE = "Cannot connect to mongodb server on host {} port {}";

    public MongoDBConnectionException(String port, Integer host) {
        super(MessageFormat.format(MESSAGE, port, host));
    }
}
