package com.sharethis.mongodb.migration;


import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.sharethis.mongodb.connection.MongoConnectionSettings;
import com.sharethis.mongodb.connection.MongoConnectionSettingsInitializer;
import com.sharethis.mongodb.dao.MigrationDao;
import com.sharethis.mongodb.dao.TargetDao;
import com.sharethis.mongodb.exception.*;
import com.sharethis.mongodb.file.FileReader;
import com.sharethis.mongodb.input.InputParametersVerifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class MigrationManagerTest {

    private static String[] args = new String[2];
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @SuppressWarnings("unchecked")
    private File scriptsFolder;

    @InjectMocks
    @Autowired
    private MigrationManager migrationManager;

    @Mock
    InputParametersVerifier inputParametersVerifier;
    @Mock
    MongoConnectionSettingsInitializer mongoConnectionSettingsInitializer;
    @Mock
    MongoConnectionSettings mongoConnectionSettings;
    @Mock
    MongoConnectionSettings mongoConnectionSettingsMock;
    @Mock
    private MigrationReader migrationReader;
    @Mock
    TargetDao targetDao;
    @Mock
    MigrationDao migrationDao;

    @Mock
    MongoClient mongoClient;
    @Mock
    MongoClientFactory mongoClientFactory;

    @Mock
    private MigrationManager migrationManagerMock;
    @Mock
    private FileReader fileReader;

    @Mock
    List<String> mockedAvailable;
    @Mock
    List<String> mockedApplied;

    List<String> notApplied = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

        scriptsFolder = tmpFolder.newFolder("scripts");

        tmpFolder.newFile("/scripts/1.mongo");
        tmpFolder.newFile("/scripts/2.mongo");

        when(mongoConnectionSettingsInitializer.initMongoConnectionSettings("blah")).thenReturn(mongoConnectionSettings);
        when(mongoConnectionSettingsInitializer.initMongoConnectionSettings("wrong_path")).thenReturn(mongoConnectionSettingsMock);
        doNothing().when(mongoConnectionSettingsInitializer).verifyMongoConnectionSettings(any(MongoConnectionSettings.class));

        when(mongoClientFactory.getMongoClient(mongoConnectionSettings)).thenReturn(mongoClient);
        when(mongoClientFactory.getMongoClient(mongoConnectionSettingsMock)).thenThrow(MongoDBConnectionException.class);

        when(mongoConnectionSettings.getHostname()).thenReturn("wrond.host");
        when(mongoConnectionSettings.getPort()).thenReturn(5);

        when(fileReader.getFileAsString(any(String.class))).thenReturn("");
        when(fileReader.getFileAsString(scriptsFolder.getAbsolutePath() + "/" + "3.mongo")).thenThrow(MigrationScriptNotFoundException.class);

        notApplied.add("1.mongo");
        when(migrationReader.findNotApplied(any(List.class), any(List.class))).thenReturn(notApplied);

        doNothing().when(targetDao).executeScript(eq("blah script"));
        doNothing().when(migrationDao).addAppliedChanges(any(DBCollection.class), any(MigrationModel.class));

    }



    @Test(expected = MongoDBConnectionException.class)
    public void testMongoDBConnectionException() throws Exception {
        args[0] = "wrong_path";
        args[1] = "foo/blah";
        migrationManager.migrate(args);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMigrationManagerFlow() throws Exception {
        args[0] = "blah";
        args[1] = "foo/blah";


        migrationManager.migrate(args);
        verify(inputParametersVerifier).verifyInputResources((String [])any());
        verify(mongoConnectionSettingsInitializer).initMongoConnectionSettings(any(String.class));
        verify(migrationReader).getMigrationNames(any(String.class));
        verify(migrationReader).getMigrationNames(any(String.class));
        verify(migrationDao).createOrUpdateCollection(MigrationSettings.APPLIED_MIGRATIONS_COLLECTION);
        verify(migrationDao).getAppliedChangesNames(any(DBCollection.class));
        verify(migrationReader).findNotApplied(any(List.class), any(List.class));

    }

    @Test
    public void testGetMigrations() throws MigrationScriptNotFoundException {
        Date date = new Date();
        List<String> notApplied = new ArrayList<>();
        notApplied.add("1.mongo");
        notApplied.add("2.mongo");
        Assert.assertEquals(2, migrationManager.getMigrations(scriptsFolder.getAbsolutePath() + "/", notApplied, date).size());
    }

    @Test(expected = MigrationScriptNotFoundException.class)
    public void testGetMigrationsFail() throws MigrationScriptNotFoundException {
        Date date = new Date();
        List<String> notApplied = new ArrayList<>();
        notApplied.add("3.mongo");
        migrationManager.getMigrations(scriptsFolder.getAbsolutePath() + "/", notApplied, date);
    }

    @Test
    public void testGetCurrentDate() {
        Calendar calendar = getCalendar();

        Assert.assertEquals(calendar.getTime(), migrationManager.getCurrentDate());
    }

    @Test
    public void testZeroNewMigrations() {
        List<String> empty = new ArrayList<>();
        List<String> notEmpty = new ArrayList<>();
        notEmpty.add("migration");

        Assert.assertFalse(migrationManager.isThereNewMigrations(empty));
        Assert.assertTrue(migrationManager.isThereNewMigrations(notEmpty));
    }

    @Test
    public void testApplyMigration() {
        List<MigrationModel> models = new ArrayList<>();
        models.add(new MigrationModel(new Date(), "blah", "blah script"));

        migrationManager.applyNewMigrations(any(DBCollection.class), models);
    }

    private Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
