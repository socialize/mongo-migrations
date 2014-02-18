package com.sharethis.mongodb.exception;

import java.net.UnknownHostException;
import java.text.MessageFormat;

public class MongoDBConnectionException extends UnknownHostException {
    private static final String MESSAGE = "Cannot connect to mongodb server on host {0} port {1}";

    public MongoDBConnectionException(String port, Integer host) {
        super(MessageFormat.format(MESSAGE, port, host));
    }
}
