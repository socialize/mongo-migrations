package com.sharethis.mongodb.connection;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by nbarabash on 2/14/14.
 */
public class MongoConnectionSettingsUtil {

    private static Logger log = LoggerFactory.getLogger(MongoConnectionSettingsUtil.class);

    public static MongoConnectionSettings initMongoConnectionSettings(String propertiesFieLocation) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(propertiesFieLocation)));

        String host = properties.getProperty(MongoProperties.MONGO_HOST);
        Integer port = Integer.parseInt(properties.getProperty(MongoProperties.MONGO_PORT));
        String db = properties.getProperty(MongoProperties.MONGO_DB);
        String user = properties.getProperty(MongoProperties.MONGO_USERNAME);
        String pwd = properties.getProperty(MongoProperties.MONGO_PASSWORD);
        MongoConnectionSettings mcs = new MongoConnectionSettings(host, port, db, user, pwd);
        verifyMongoConnectionSettings(mcs);
        return mcs;
    }

    public static void verifyMongoConnectionSettings(MongoConnectionSettings settings) {
        if (StringUtils.isEmpty(settings.getHostname())) {
            log.error("--- Property {} is missed in properties file. Add it and try again.", MongoProperties.MONGO_HOST);
            sayBye();
        }
        if (settings.getPort() == null || settings.getPort() == 0) {
            log.error("--- Property is missed in properties file. Add it and try again.", MongoProperties.MONGO_PORT);
            sayBye();
        }
        if (StringUtils.isEmpty(settings.getDatabase())) {
            log.error("--- Property {} is missed in properties file. Add it and try again.", MongoProperties.MONGO_DB);
            sayBye();
        }
    }

    private static void sayBye() {
        log.error("--- Application will close");
        System.exit(0);
    }
}
