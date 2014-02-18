mongo-migrations
================


Mongo Migrations is a tool for running mongo migration scripts.
This tool also persists state of migration - persists names of already ran migration scripts.

### Required environment
    maven, java7

### How to build
 Run from project root.

    mvn clean package

### How to run locally with examples from source folder
 Copy required resources

    cp src/main/resources/example/mongo.properties target
    cp -R src/main/resources/example/mongo target
    cd target

 Run migration tool

    java  -jar mongo-migrations-1.0-SNAPSHOT.jar mongo.properties mongo/migrationSet.mongo


### Filesystem structure example for running migration tool with command above

    /
      mongo-migrations-1.0-SNAPSHOT.jar   - mongo migration tool
      mongo.properties                    - mongo connection properties
      migrationSet.mongo                  - file with set of available migrations
      scripts /                           - directory with migration script files
                001.mongo                   - mongo migration script identified as 001
                002.mongo                   - mongo migration script identified as 002
                003.mongo                   - mongo migration script identified as 003


### Example of mongo.properties

    mongo.host=localhost
    mongo.port=27017
    mongo.db=example
    mongo.username=
    mongo.password=

### Example of migrationSet.mongo

    001.mongo
    002.mongo
    003.mongo

### Example of migration script file(001.mongo)

    db.createCollection("analytic_events");
    db.createCollection("share_events");
    db.analytic_events.ensureIndex({
    	"_id.ap" : 1,
    	"_id.dt" : 1,
    	"_id.ch" : 1,
        "_id.ev" : 1
    });
