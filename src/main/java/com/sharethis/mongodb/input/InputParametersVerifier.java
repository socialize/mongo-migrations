package com.sharethis.mongodb.input;

import com.sharethis.mongodb.exception.ChangeSetNotFoundException;
import com.sharethis.mongodb.exception.MongoConnectionSettingsNotFoundException;
import com.sharethis.mongodb.exception.PropertyNotFoundException;
import com.sharethis.mongodb.exception.WrongInputParametersException;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class InputParametersVerifier {

    public void verifyInputResources(String[] args) throws PropertyNotFoundException, ChangeSetNotFoundException, MongoConnectionSettingsNotFoundException, WrongInputParametersException {
        if (args.length < 2) {
            throw new WrongInputParametersException();
        }

        if (!new File(args[0]).exists()) {
            throw new MongoConnectionSettingsNotFoundException(args[0]);
        }

        if (!new File(args[1]).exists()) {
            throw new ChangeSetNotFoundException(args[1]);
        }

        String scriptsFolder = args[1].substring(0, args[1].lastIndexOf("/")) + "/scripts";
        if (!new File(scriptsFolder).exists()) {
            throw new ChangeSetNotFoundException(scriptsFolder);
        }
    }
}
