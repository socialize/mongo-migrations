package com.sharethis.mongodb.migration;


import com.sharethis.mongodb.exception.MongoDBConnectionException;
import com.sharethis.mongodb.util.PropertyHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

public class MigrationManagerTest {

    private static String[] args = new String[2];
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    private File mongoSettings;
    private File migrationsSet;
    @SuppressWarnings("unchecked")
    private File scriptsFolder;

    private MigrationManager manager;

    @Before
    public void init() throws Exception {
        manager = new MigrationManager();

        mongoSettings = tmpFolder.newFile("mongo.settings");
        migrationsSet = tmpFolder.newFile("changeSet.mongo");
        scriptsFolder = tmpFolder.newFolder("scripts");

        args[0] = mongoSettings.getAbsolutePath();
        args[1] = migrationsSet.getAbsolutePath();

        PropertyHelper.prepareProperties(mongoSettings.getAbsoluteFile());
    }

    @Test(expected = MongoDBConnectionException.class)
    public void testMongoDBConnectionException() throws Exception {
        manager.migrate(args);
    }

}
