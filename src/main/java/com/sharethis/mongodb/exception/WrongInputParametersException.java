package com.sharethis.mongodb.exception;

import java.text.MessageFormat;

public class WrongInputParametersException extends Exception {
    private static final String MESSAGE = "There is missed one of required parameters \n" +
            "Mongo migration tool requires 2 input parameters: \n" +
            "1-st - the path to database connection settings \n" +
            "2-nd - path to file with change set. You passed {0} :";

    public WrongInputParametersException(String[] args) {

        super(MessageFormat.format(MESSAGE, args.length));
    }
}
