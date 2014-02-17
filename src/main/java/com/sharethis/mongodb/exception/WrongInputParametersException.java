package com.sharethis.mongodb.exception;

public class WrongInputParametersException extends Exception {
    private static String message = "--- There is missed one of required parameters \n" +
            "--- Mongo migration tool requires 2 input parameters: \n" +
            "--- 1-st - the path to database connection settings \n" +
            "--- 2-nd - path to file with change set ";

    public WrongInputParametersException() {
        super(message);
    }
}
