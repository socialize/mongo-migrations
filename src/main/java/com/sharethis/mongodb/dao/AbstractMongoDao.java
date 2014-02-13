package com.sharethis.mongodb.dao;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import com.sharethis.mongodb.MongoConnectionSettings;

import java.net.UnknownHostException;

/**
 * Created by nbarabash on 2/13/14.
 */
public abstract class AbstractMongoDao {

    protected Mongo openConnection(MongoConnectionSettings connectionSettings) throws UnknownHostException {

        ServerAddress serverAddr = (connectionSettings.getPort() != null) ? new ServerAddress(
                connectionSettings.getHostname(), connectionSettings.getPort().intValue()) : new ServerAddress(
                connectionSettings.getHostname());

        Mongo mongo = (connectionSettings.getOptions() != null) ? new Mongo(serverAddr, connectionSettings.getOptions())
                : new Mongo(serverAddr);

        return mongo;
    }
}
