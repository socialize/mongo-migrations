package com.sharethis.mongodb.connection;

import com.sharethis.mongodb.exception.MigrationIOException;
import com.sharethis.mongodb.exception.PropertyNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class MongoConnectionSettingsInitializerTest {
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    private MongoConnectionSettingsInitializer initializer = new MongoConnectionSettingsInitializer();

    private File mongoSettings;

    @Before
    public void init() throws IOException {
        mongoSettings = tmpFolder.newFile("mongo.properties");

        prepareProperties();

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


    private void prepareProperties() throws FileNotFoundException {

        PrintWriter printer = new PrintWriter(mongoSettings);
        Map<String, String> properties = new HashMap<>();
        properties.put(MongoProperties.MONGO_HOST, "host");
        properties.put(MongoProperties.MONGO_PORT, "123");
        properties.put(MongoProperties.MONGO_DB, "db");

        for(Map.Entry<String, String> property: properties.entrySet()){
            printer.write(property.getKey() + "=" + property.getValue() + "\n");
        }

        printer.flush();
        printer.close();
    }
}
