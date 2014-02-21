package com.sharethis.mongodb.file;

import com.sharethis.mongodb.connection.MongoProperties;
import com.sharethis.mongodb.exception.ChangeSetNotFoundException;
import com.sharethis.mongodb.exception.MigrationScriptNotFoundException;
import com.sharethis.mongodb.util.PropertyHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class FileReaderTest {

    public static final String NO_FILE = "no_file";
    private FileReader reader = new FileReader();
    @Mock
    private FileReader mockedReader;

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    private File mongoSettings;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

        mongoSettings = tmpFolder.newFile("file");

        PropertyHelper.prepareProperties(mongoSettings);
    }

    @Test(expected = MigrationScriptNotFoundException.class)
    public void testGetFileAsStringException() throws Exception {
        reader.getFileAsString(NO_FILE);
    }

    @Test
    public void testGetFileAsString() throws Exception {
        String properties = MongoProperties.MONGO_HOST + "=" + "host" + "\n" +
                MongoProperties.MONGO_PORT + "=" + "123" + "\n" +
                MongoProperties.MONGO_DB + "=" + "db";

        Assert.assertEquals(properties, reader.getFileAsString(mongoSettings.getAbsolutePath()));
    }

    @Test(expected = ChangeSetNotFoundException.class)
    public void testGetFileAsLinesException() throws Exception {
        reader.getFileAsLines(NO_FILE);
    }

    @Test
    public void testGetFileAsLines() throws Exception {
        List<String> properties = new ArrayList<>();
        properties.add(MongoProperties.MONGO_HOST + "=" + "host");
        properties.add(MongoProperties.MONGO_PORT + "=" + "123");
        properties.add(MongoProperties.MONGO_DB + "=" + "db");

        Assert.assertEquals(properties, reader.getFileAsLines(mongoSettings.getAbsolutePath()));
    }
}
