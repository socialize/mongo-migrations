package com.sharethis.mongodb.migration;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.sharethis.mongodb.connection.MongoConnectionSettings;
import com.sharethis.mongodb.connection.MongoConnectionSettingsUtil;
import com.sharethis.mongodb.dao.MigrationDao;
import com.sharethis.mongodb.dao.TargetDao;
import com.sharethis.mongodb.exception.*;
import com.sharethis.mongodb.file.FileReader;
import com.sharethis.mongodb.input.InputParametersVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.*;


public class MigrationManager {

    public static final String SCRIPTS = "/scripts/";
    private static Logger log = LoggerFactory.getLogger(MigrationManager.class);

    public void migrate(String[] inputParams) throws PropertyNotFoundException, ChangeSetNotFoundException, MongoDBConnectionSettingsNotFoundException, WrongInputParametersException, MigrationIOException, MongoDBConnectionException, MigrationScriptNotFoundException {

        log.info("Congrats this is mongodb migration tool");

        new InputParametersVerifier().verifyInputResources(inputParams);

        String mongoConnectionFile = inputParams[0];
        String changeSetPath = inputParams[1];
        String scriptsFolder = changeSetPath.substring(0, changeSetPath.lastIndexOf("/")) + SCRIPTS;

        log.info("Initializing MongoDB connection settings");
        MongoConnectionSettings mongoConnectionSettings = new MongoConnectionSettingsUtil().initMongoConnectionSettings(mongoConnectionFile);

        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient(mongoConnectionSettings.getHostname(), mongoConnectionSettings.getPort());
        } catch (UnknownHostException uhex) {
            throw new MongoDBConnectionException(mongoConnectionSettings.getHostname(), mongoConnectionSettings.getPort());

        }

        DB targetDB = mongoClient.getDB(mongoConnectionSettings.getDatabase());
        TargetDao targetDao = new TargetDao(targetDB);

        MigrationReader migrationReader = new MigrationReader();
        List<String> availableMigrationsNames = migrationReader.getMigrationNames(changeSetPath);
        log.info("Available migration(s): {}", availableMigrationsNames.toString());

        MigrationDao migrationDao = new MigrationDao(mongoClient.getDB(MigrationSettings.APPLIED_MIGRATIONS_DB_NAME));
        DBCollection migrationCollection = migrationDao.createOrUpdateCollection(MigrationSettings.APPLIED_MIGRATIONS_COLLECTION);

        List<String> appliedMigrationsNames = migrationDao.getAppliedChangesNames(migrationCollection);
        log.info("Applied migration(s): {}", appliedMigrationsNames.toString());


        List<String> newMigrationsNames = migrationReader.findNotApplied(availableMigrationsNames, appliedMigrationsNames);

        if (newMigrationsNames.isEmpty()) {
            log.info("Database is up-to-date");
            return;
        } else {
            log.info("There where found {} migration files to be applied", newMigrationsNames.size());
            log.info("Migration files to be applied are : " + newMigrationsNames.toString());
        }

        List<MigrationModel> migrations = getMigrations(scriptsFolder, newMigrationsNames, getCurrentDate());


        for (MigrationModel migration : migrations) {
            log.info("Applying migration file : {}", migration.getScriptName());
            log.info("\n" + migration.getScriptBody());
            targetDao.executeScript(migration.getScriptBody());
            migrationDao.addAppliedChanges(migrationCollection, migration);
        }

        log.info("BD migration was successful");

    }

    private List<MigrationModel> getMigrations(String scriptsFolder, List<String> notYetApplied, Date date) throws MigrationScriptNotFoundException {
        List<MigrationModel> migrations = new ArrayList<>();
        for (String change : notYetApplied) {
            String body = new FileReader().getFileAsString(scriptsFolder + change);
            migrations.add(new MigrationModel(date, change, body));
        }
        return migrations;
    }

    private static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTime();
    }

}
