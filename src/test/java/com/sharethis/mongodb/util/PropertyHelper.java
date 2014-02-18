package com.sharethis.mongodb.util;

import com.sharethis.mongodb.connection.MongoProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PropertyHelper {

    public static void prepareProperties(File mongoSettings) throws FileNotFoundException {

        PrintWriter printer = new PrintWriter(mongoSettings);
        String properties = MongoProperties.MONGO_HOST + "=host\n"
                + MongoProperties.MONGO_PORT + "=123\n"
                + MongoProperties.MONGO_DB + "=db";

        printer.write(properties);

        printer.flush();
        printer.close();
    }

    public static void prepareChangeSet(File changeSet) throws FileNotFoundException {

        PrintWriter printer = new PrintWriter(changeSet);
        String migrationNames = "name1.mongo\nname2.mongo\nname3.mongo";

        printer.write(migrationNames);

        printer.flush();
        printer.close();
    }
}
