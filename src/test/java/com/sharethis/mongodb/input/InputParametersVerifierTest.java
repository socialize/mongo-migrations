package com.sharethis.mongodb.input;

import com.sharethis.mongodb.exception.ChangeSetNotFoundException;
import com.sharethis.mongodb.exception.MongoDBConnectionSettingsNotFoundException;
import com.sharethis.mongodb.exception.WrongInputParametersException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;


public class InputParametersVerifierTest {

    private InputParametersVerifier verifier;
    private static String[] args = new String[2];
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    private File mongoSettings;
    private File migrationsSet;
    private File scriptsFolder;

    @Before
    public void init() throws Exception {
        verifier = new InputParametersVerifier();

        mongoSettings = tmpFolder.newFile("mongo.settings");
        migrationsSet = tmpFolder.newFile("changeSet.mongo");
        scriptsFolder = tmpFolder.newFolder("scripts");

    }

//    @After
//    public void destroy() {
//        mongoSettings.delete();
//        migrationsSet.delete();
//        scriptsFolder.delete();
//        tmpFolder.delete();
//    }

    @Test
    public void verifyInputResources() throws Exception {
        args[0] = mongoSettings.getAbsolutePath();
        args[1] = migrationsSet.getAbsolutePath();
        verifier.verifyInputResources(args);
        Assert.assertTrue(true);
    }

    @Test(expected = WrongInputParametersException.class)
    public void verifyWrongInputParametersException() throws Exception {
        verifier.verifyInputResources( new String[1]);
    }

    @Test(expected = MongoDBConnectionSettingsNotFoundException.class)
    public void verifyMongoDBConnectionSettingsNotFoundException() throws Exception {

        verifier.verifyInputResources( new String[] {"blah", "foo"});
    }

    @Test(expected = ChangeSetNotFoundException.class)
    public void verifyChangeSetNotFoundException() throws Exception {

        verifier.verifyInputResources( new String[] {mongoSettings.getAbsolutePath(), "foo"});
    }

    @SuppressWarnings("unchecked")
    @Test(expected = ChangeSetNotFoundException.class)
    public void verifyScriptChangeSetNotFoundException() throws Exception {
        scriptsFolder.delete();
        verifier.verifyInputResources( new String[] {mongoSettings.getAbsolutePath(), migrationsSet.getAbsolutePath()});
    }
}
