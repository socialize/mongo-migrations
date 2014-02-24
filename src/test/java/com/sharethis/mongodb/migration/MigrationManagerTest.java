package com.sharethis.mongodb.migration;

import com.sharethis.mongodb.util.PropertyHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class MigrationManagerTest {

    private static String[] args = new String[2];
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    private File mongoSettings;
    private File migrationsSet;
    @SuppressWarnings("unchecked")
    private File scriptsFolder;

    @Autowired
    private MigrationManager manager;

    @Before
    public void init() throws Exception {

        mongoSettings = tmpFolder.newFile(MigrationFiles.MONGO_PROPERTIES);
        migrationsSet = tmpFolder.newFile(MigrationFiles.MIGRATION_SET_MONGO);
        scriptsFolder = tmpFolder.newFolder(MigrationFiles.SCRIPTS);

        args[0] = mongoSettings.getAbsolutePath();
        args[1] = migrationsSet.getAbsolutePath();

        PropertyHelper.prepareProperties(mongoSettings.getAbsoluteFile());
    }

    //@Test(expected = MongoDBConnectionException.class)
    public void testMongoDBConnectionException() throws Exception {
        manager.migrate(args);
    }

}
