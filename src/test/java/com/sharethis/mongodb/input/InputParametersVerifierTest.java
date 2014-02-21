package com.sharethis.mongodb.input;

import com.sharethis.mongodb.exception.ChangeSetNotFoundException;
import com.sharethis.mongodb.exception.MongoConnectionSettingsNotFoundException;
import com.sharethis.mongodb.exception.WrongInputParametersException;
import com.sharethis.mongodb.migration.MigrationFiles;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class InputParametersVerifierTest {
    @Autowired
    private InputParametersVerifier verifier;
    private static String[] args = new String[2];
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    private File mongoSettings;
    private File migrationsSet;
    private File scriptsFolder;

    @Before
    public void init() throws Exception {
        mongoSettings = tmpFolder.newFile(MigrationFiles.MONGO_PROPERTIES);
        migrationsSet = tmpFolder.newFile(MigrationFiles.MIGRATION_SET_MONGO);
        scriptsFolder = tmpFolder.newFolder(MigrationFiles.SCRIPTS);

    }

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

    @Test(expected = MongoConnectionSettingsNotFoundException.class)
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
