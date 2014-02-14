package com.sharethis.mongodb.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by nbarabash on 2/14/14.
 */
public class InputParametersVerifier {

    private static Logger log = LoggerFactory.getLogger(InputParametersVerifier.class);

    public static void verifyInputResources(String[] args) {
        if (!new File(args[0]).exists()) {
            log.error("--- Application can not find mongo connection settings file : {}", args[0]);
            sayBye();
        }

        if (!new File(args[1]).exists()) {
            log.error("--- Application can not find mongo change set file : {}", args[1]);
            sayBye();
        }

        String scriptsFolder = args[1].substring(0, args[1].lastIndexOf("/")) + "/scripts";
        if (!new File(scriptsFolder).exists()) {
            log.error("--- Application can not find 'scripts' folder with mongodb migration scripts : {}", scriptsFolder);
            sayBye();
        }
    }


    public static void verifyInputParameters(String[] args) {
        if (args.length < 2) {
            log.error("--- There is missed one of required parameters");
            log.error("--- Mongo migration tool requires 2 input parameters: ");
            log.error("--- 1-st - the path to database connection settings ");
            log.error("--- 2-nd - path to file with change set ");
            sayBye();
        }
    }

    private static void sayBye() {
        log.error("--- Application will close");
        System.exit(0);
    }
}
