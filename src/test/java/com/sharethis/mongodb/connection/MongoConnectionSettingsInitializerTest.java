package com.sharethis.mongodb.connection;

import com.sharethis.mongodb.exception.MigrationIOException;
import com.sharethis.mongodb.exception.PropertyNotFoundException;
import com.sharethis.mongodb.migration.MigrationFiles;
import com.sharethis.mongodb.util.PropertyHelper;
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
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class MongoConnectionSettingsInitializerTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Autowired
    private MongoConnectionSettingsInitializer initializer;

    private File mongoSettings;

    @Before
    public void init() throws IOException {
        mongoSettings = tmpFolder.newFile(MigrationFiles.MONGO_PROPERTIES);

        PropertyHelper.prepareProperties(mongoSettings);

    }

    @Test
    public void testSettingsFile() throws Exception {
        initializer.initMongoConnectionSettings(mongoSettings.getAbsolutePath());
        Assert.assertTrue(true);// reading and validation passed, no exception throw
    }

    @Test(expected = MigrationIOException.class)
    public void testNoSettingsFile() throws Exception {
        initializer.initMongoConnectionSettings("blah");
    }

    @Test()
    public void testProperty() throws Exception {
        MongoConnectionSettings mongoSettings = new MongoConnectionSettings("host", 1, "db", "", "");
        initializer.verifyMongoConnectionSettings(mongoSettings);
    }

    @Test(expected = PropertyNotFoundException.class)
    public void testNoHostProperty() throws Exception {
        MongoConnectionSettings mongoSettings = new MongoConnectionSettings("", 1, "db", "", "");
        initializer.verifyMongoConnectionSettings(mongoSettings);
    }

    @Test(expected = PropertyNotFoundException.class)
    public void testNoPortProperty() throws Exception {
        MongoConnectionSettings mongoSettings = new MongoConnectionSettings("host", 0, "db", "", "");
        initializer.verifyMongoConnectionSettings(mongoSettings);
    }

    @Test(expected = PropertyNotFoundException.class)
    public void testNoDBProperty() throws Exception {
        MongoConnectionSettings mongoSettings = new MongoConnectionSettings("host", 1, "", "", "");
        initializer.verifyMongoConnectionSettings(mongoSettings);
    }
}
