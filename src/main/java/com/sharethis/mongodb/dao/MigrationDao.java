package com.sharethis.mongodb.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.sharethis.mongodb.migration.MigrationModel;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MigrationDao extends AbstractMongoDao {

    public MigrationDao() {
    }

    public List<String> getAppliedChangesNames(DBCollection migrationCollection) {

        BasicDBObject query = new BasicDBObject(); // because you have no conditions
        BasicDBObject fields = new BasicDBObject("Name", 1);
        List<String> appliedChangeSet = new LinkedList<>();

        DBCursor dbCursor = migrationCollection.find(query, fields);
        while (dbCursor.hasNext()) {
            DBObject obj = dbCursor.next();
            appliedChangeSet.add(obj.get("Name").toString());
        }
        return appliedChangeSet;
    }

    public DBCollection createOrUpdateCollection(String collectionName) {
        return db.createCollection(collectionName, null);
    }

    public void addAppliedChanges(DBCollection collection, MigrationModel change) {
        DBObject person = BasicDBObjectBuilder.start()
                .add("Name", change.getScriptName())
                .add("Body", change.getScriptBody())
                .add("AppliedDate", change.getAppliedDate())
                .get();
        collection.save(person);
    }


}
