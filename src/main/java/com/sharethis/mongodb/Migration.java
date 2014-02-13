package com.sharethis.mongodb;


import com.mongodb.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

public class Migration {
    public static final String MONGO_HOST = "mongo.host";
    public static final String MONGO_PORT = "mongo.port";
    public static final String MONGO_DB = "mongo.db";
    public static final String MONGO_USERNAME = "mongo.username";
    public static final String MONGO_PASSWORD = "mongo.password";
    public final static String APPLIED_MIGRATIONS = "appliedMigrations";

    private static Logger log = LoggerFactory.getLogger(Migration.class);


    public static void main(String[] args) {

        log.info("--- Congrats this is mongodb migration tool");

        verifyInputParameters(args);

        verifyInputResources(args);

        MongoConnectionSettings mcs = null;

        try {
            log.info("--- Initializing MongoDB connection settings");
            mcs = initMongoConnectionSettings(args[0]);
            log.info("--- MongoDB connection settings initialized succesfully");
        } catch (IOException ioex) {
            log.error("--- Initializing  MongoDB connection settings failed");
            log.error("", ioex);
            log.error("--- Application will close");
            System.exit(0);
        }

        MongoClient mClient = null;

        try {
            mClient = new MongoClient(mcs.getHostname(), mcs.getPort());
        } catch (UnknownHostException uhex) {
            log.error("", uhex);
            log.error("-- Application will close");
            System.exit(0);
        }

        DB mdb = mClient.getDB(mcs.getDatabase());

        List<String> availableChangeSet = new LinkedList<>();
        try {
            availableChangeSet = ChangeSetReader.getChangeSet(args[1]);
        } catch (FileNotFoundException fnex) {
            log.error("", fnex);
            log.error("-- Application will close");
            System.exit(0);
        }

        log.info("--- Available changes sre : " + availableChangeSet.toString());

        DB migrationdb = mClient.getDB(APPLIED_MIGRATIONS);
        migrationdb.createCollection(APPLIED_MIGRATIONS, null);

        DBCollection collection = migrationdb.getCollection(APPLIED_MIGRATIONS);

        BasicDBObject query = new BasicDBObject(); // because you have no conditions
        BasicDBObject fields = new BasicDBObject("Name", 1);
        List<String> appliedChangeSet = new LinkedList<>();

        DBCursor dbCursor = collection.find(query, fields);
        while (dbCursor.hasNext()) {
            DBObject obj = dbCursor.next();
            appliedChangeSet.add(obj.get("Name").toString());
        }

        log.info("--- Applied changes are : " + appliedChangeSet.toString());


        availableChangeSet.removeAll(appliedChangeSet);

        if(availableChangeSet.isEmpty()) {
            log.info("--- There is not any new change to be applied");
            log.info("--- Application will close");
            System.exit(0);
        } else {
            log.info("--- There where found  " + appliedChangeSet.size() + " change files to be applied");
            log.info("--- Change file to be applied are : " + availableChangeSet.toString());
        }

        String scriptsFolder = args[1].substring(0, args[1].lastIndexOf("/")) + "/scripts/";
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date date = calendar.getTime();
        try {
        for(String change : availableChangeSet) {
            log.info("--- Applying change file : " + change);
            String body = new Scanner(new File(scriptsFolder + change), "UTF-8").useDelimiter("\\Z").next();
            log.info("--- The scripts to be applied are : \n " + body);
            DBObject person = BasicDBObjectBuilder.start()
                    .add("Name", change)
                    .add("Body", body)
                    .add("AppliedDate", date)
                    .get();
            collection.save(person);
        }
        } catch(FileNotFoundException fnfex) {
            log.error("", fnfex);
        }

    }

    private static void verifyInputResources(String[] args) {
        if (!new File(args[0]).exists()) {
            log.error("--- Application can not find mongo connection settings file : " + args[0]);
            log.error("--- Application will close");
            System.exit(0);
        }

        if (!new File(args[1]).exists()) {
            log.error("--- Application can not find mongo change set file : " + args[1]);
            log.error("--- Application will close");
            System.exit(0);
        }

        String scriptsFolder = args[1].substring(0, args[1].lastIndexOf("/")) + "/scripts";
        if (!new File(scriptsFolder).exists()) {
            log.error("--- Application can not find 'scripts' folder with mongodb migration scripts : " + scriptsFolder);
            log.error("--- Application will close");
            System.exit(0);
        }
    }

    private static void verifyInputParameters(String[] args) {
        if (args.length < 2) {
            log.error("--- There is missed one of required parameters");
            log.error("--- Mongo migration tool requires 2 input parameters: ");
            log.error("--- 1-st - the path to database connection settings ");
            log.error("--- 2-nd - path to file with change set ");
            log.error("--- Application will close");
            System.exit(0);
        }
    }

    private static MongoConnectionSettings initMongoConnectionSettings(String propertiesFieLocation) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(propertiesFieLocation)));

        String host = properties.getProperty(MONGO_HOST);
        Integer port = Integer.parseInt(properties.getProperty(MONGO_PORT));
        String db = properties.getProperty(MONGO_DB);
        String user = properties.getProperty(MONGO_USERNAME);
        String pwd = properties.getProperty(MONGO_PASSWORD);
        MongoConnectionSettings mcs = new MongoConnectionSettings(host, port, db, user, pwd);
        verifyMongoConnectionSettings(mcs);
        return mcs;
    }

    public static void verifyMongoConnectionSettings(MongoConnectionSettings settings) {
        if(StringUtils.isEmpty(settings.getHostname())) {
            log.error("--- Property " + MONGO_HOST + " is missed in properties file. Add it and try again.");
            log.error("--- Application will close");
            System.exit(0);
        }
        if(settings.getPort() == null ||  settings.getPort() == 0) {
            log.error("--- Property " + MONGO_PORT+ " is missed in properties file. Add it and try again.");
            log.error("--- Application will close");
            System.exit(0);
        }
        if(StringUtils.isEmpty(settings.getDatabase())) {
            log.error("--- Property " + MONGO_DB + " is missed in properties file. Add it and try again.");
            log.error("--- Application will close");
            System.exit(0);
        }
    }
}
