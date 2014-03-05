package com.sharethis.mongodb.migration;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.sharethis.mongodb.connection.MongoConnectionSettings;
import com.sharethis.mongodb.connection.MongoConnectionSettingsInitializer;
import com.sharethis.mongodb.dao.MigrationDao;
import com.sharethis.mongodb.dao.TargetDao;
import com.sharethis.mongodb.exception.WrongInputParametersException;
import com.sharethis.mongodb.exception.PropertyNotFoundException;
import com.sharethis.mongodb.exception.MongoDBConnectionException;
import com.sharethis.mongodb.exception.ChangeSetNotFoundException;
import com.sharethis.mongodb.exception.MigrationIOException;
import com.sharethis.mongodb.exception.MigrationScriptNotFoundException;
import com.sharethis.mongodb.exception.MongoConnectionSettingsNotFoundException;

import com.sharethis.mongodb.file.FileReader;
import com.sharethis.mongodb.input.InputParametersVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.Date;

@Component
public class MigrationManager {

    public static final String SCRIPTS = "/scripts/";
    private static Logger log = LoggerFactory.getLogger(MigrationManager.class);

    @Autowired
    private InputParametersVerifier inputParametersVerifier;
    @Autowired
    private MongoConnectionSettingsInitializer mongoConnectionSettingsInitializer;
    @Autowired
    private MigrationReader migrationReader;
    @Autowired
    TargetDao targetDao;
    @Autowired
    MigrationDao migrationDao;
    @Autowired
    MongoClientFactory mongoClientFactory;
    @Autowired
    FileReader fileReader;

    public void migrate(String[] inputParams) throws PropertyNotFoundException, ChangeSetNotFoundException, MongoConnectionSettingsNotFoundException, WrongInputParametersException, MigrationIOException, MongoDBConnectionException, MigrationScriptNotFoundException {

        log.info("Congrats this is mongodb migration tool");

        inputParametersVerifier.verifyInputResources(inputParams);

        String mongoConnectionFile = inputParams[0];
        String changeSetPath = inputParams[1];
        String scriptsFolder = changeSetPath.substring(0, changeSetPath.lastIndexOf("/")) + SCRIPTS;

        log.info("Initializing MongoDB connection settings");
        MongoConnectionSettings mongoConnectionSettings = mongoConnectionSettingsInitializer.initMongoConnectionSettings(mongoConnectionFile);

        initDao(mongoConnectionSettings);

        List<String> availableMigrationsNames = migrationReader.getMigrationNames(changeSetPath);
        log.info("Available migration(s): {}", availableMigrationsNames.toString());

        DBCollection migrationCollection = migrationDao.createOrUpdateCollection(MigrationSettings.APPLIED_MIGRATIONS_COLLECTION);

        List<String> appliedMigrationsNames = migrationDao.getAppliedChangesNames(migrationCollection);
        log.info("Applied migration(s): {}", appliedMigrationsNames.toString());

        List<String> newMigrationsNames = migrationReader.findNotApplied(availableMigrationsNames, appliedMigrationsNames);

        if (!isThereNewMigrations(newMigrationsNames)) {
            return;
        }

        List<MigrationModel> migrations = getMigrations(scriptsFolder, newMigrationsNames, getCurrentDate());

        applyNewMigrations(migrationCollection, migrations);
        log.info("DB migration was successful");

    }

    void applyNewMigrations(DBCollection migrationCollection, List<MigrationModel> migrations) {
        for (MigrationModel migration : migrations) {
            log.info("Applying migration file : {}", migration.getScriptName());
            log.info("\n" + migration.getScriptBody());
            targetDao.executeScript(migration.getScriptBody());
            migrationDao.addAppliedChanges(migrationCollection, migration);
        }
    }

    boolean isThereNewMigrations(List<String> newMigrationsNames) {
        if (newMigrationsNames.isEmpty()) {
            log.info("Database is up-to-date");
            return false;
        } else {
            log.info("There where {} migration files found to be applied", newMigrationsNames.size());
            log.info("Migration files to be applied are : " + newMigrationsNames.toString());
            return true;
        }
    }

    void initDao(MongoConnectionSettings mongoConnectionSettings) throws MongoDBConnectionException {
        MongoClient mongoClient = mongoClientFactory.getMongoClient(mongoConnectionSettings);
        DB targetDB = mongoClient.getDB(mongoConnectionSettings.getDatabase());
        targetDao.setDb(targetDB);
        migrationDao.setDb(targetDB);
    }

    List<MigrationModel> getMigrations(String scriptsFolder, List<String> notYetApplied, Date date) throws MigrationScriptNotFoundException {
        List<MigrationModel> migrations = new ArrayList<>();
        for (String change : notYetApplied) {
            String body = fileReader.getFileAsString(scriptsFolder + change);
            migrations.add(new MigrationModel(date, change, body));
        }
        return migrations;
    }

    Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
