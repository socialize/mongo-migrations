mongo-migrations
================

Mongo Migrations Java Library

### Required environment
    maven, mongo, java 7

### How to build

    mvn clean package

### How to run locally

    cp src/main/resources/mongo.properties /target
    cp -R src/main/resources/mongo /target
    cd target
    java  -jar mongo-migrations-1.0-SNAPSHOT.jar mongo.properties /mongo/changeSet.mongo
