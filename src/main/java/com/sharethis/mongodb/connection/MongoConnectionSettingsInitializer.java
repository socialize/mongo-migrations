package com.sharethis.mongodb.connection;

import com.sharethis.mongodb.exception.MigrationIOException;
import com.sharethis.mongodb.exception.PropertyNotFoundException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MongoConnectionSettingsInitializer {
    private static final String INIT_ERROR_MESSAGE = "Initializing MongoDB connection settings failed. \n";

    public MongoConnectionSettings initMongoConnectionSettings(String propertiesFieLocation) throws MigrationIOException, PropertyNotFoundException {
        Properties properties = new Properties();
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(propertiesFieLocation));
            properties.load(inStream);
        } catch (IOException ioex) {
            throw new MigrationIOException(INIT_ERROR_MESSAGE + ioex.getLocalizedMessage(), ioex.getCause());
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            }
        }

        String host = properties.getProperty(MongoProperties.MONGO_HOST);
        Integer port = Integer.parseInt(properties.getProperty(MongoProperties.MONGO_PORT, "0"));
        String db = properties.getProperty(MongoProperties.MONGO_DB);
        String user = properties.getProperty(MongoProperties.MONGO_USERNAME);
        String pwd = properties.getProperty(MongoProperties.MONGO_PASSWORD);
        MongoConnectionSettings mcs = new MongoConnectionSettings(host, port, db, user, pwd);

        verifyMongoConnectionSettings(mcs);

        return mcs;
    }

    public void verifyMongoConnectionSettings(MongoConnectionSettings settings) throws PropertyNotFoundException {
        if (StringUtils.isEmpty(settings.getHostname())) {
            throw new PropertyNotFoundException(MongoProperties.MONGO_HOST);
        }
        if (settings.getPort() == null || settings.getPort() == 0) {
            throw new PropertyNotFoundException(MongoProperties.MONGO_PORT);
        }
        if (StringUtils.isEmpty(settings.getDatabase())) {
            throw new PropertyNotFoundException(MongoProperties.MONGO_DB);
        }
    }
}
