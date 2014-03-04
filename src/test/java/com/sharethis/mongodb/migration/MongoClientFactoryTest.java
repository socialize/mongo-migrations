package com.sharethis.mongodb.migration;

import com.sharethis.mongodb.connection.MongoConnectionSettings;
import com.sharethis.mongodb.exception.MongoDBConnectionException;
import org.junit.Test;

/**
 * Created by nbarabash on 2/26/14.
 */

public class MongoClientFactoryTest {

    @Test(expected = MongoDBConnectionException.class)
    public void testFail() throws MongoDBConnectionException {
       new MongoClientFactory().getMongoClient(new MongoConnectionSettings("hostname", 100500, "database", "userName", "password"));
    }
}
