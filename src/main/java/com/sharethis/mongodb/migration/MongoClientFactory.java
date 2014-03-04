package com.sharethis.mongodb.migration;

import com.mongodb.MongoClient;
import com.sharethis.mongodb.connection.MongoConnectionSettings;
import com.sharethis.mongodb.exception.MongoDBConnectionException;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

/**
 * Created by nbarabash on 2/26/14.
 */
@Component
public class MongoClientFactory {

    MongoClient getMongoClient(MongoConnectionSettings mongoConnectionSettings) throws MongoDBConnectionException {
        try {
            return new MongoClient(mongoConnectionSettings.getHostname(), mongoConnectionSettings.getPort());
        } catch (UnknownHostException uhex) {
            throw new MongoDBConnectionException(mongoConnectionSettings.getHostname(), mongoConnectionSettings.getPort());

        }
    }
}
