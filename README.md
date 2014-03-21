mongo-migrations
================

Mongo Migrations Java Library

Mongo Migrations is 3-rd party java library and maven plugin for managing mongo migrations.
Migration state is persisted into DB (collection "applied_migrations").
To run this tool/plugin it is required to pass 2 input parameters: mongo.properties location and migrationSet.mongo location.

mongo.properties is simple properties file with mongo connection configuration.
migrationSet.mongo is a file with listed mongo migration scripts files.

Examples of such file could be fond in project(src/resources/example)


### Required environment
    maven 3, java 7

### Public key

Public key short id is 48C040FE

### How to build

    mvn clean package

### How to run as a tool locally

    java  -jar target/mongo-migrations.jar src/main/resources/example/mongo.properties src/main/resources/example/mongo/migrationSet.mongo

### How to add as plugin into project

    <plugin>
        <groupId>com.sharethis</groupId>
        <artifactId>mongomigration</artifactId>
        <version>1.0.0</version>
    </plugin>

### How to pass input parameters from pom.xml

     <configuration>
        <args>
            <arg>./src/main/resources/mongo/mongo.properties</arg>
            <arg>./src/main/resources/mongo/migrationSet.mongo</arg>
        </args>
     </configuration>

### How to pass input parameters from command line

    -Dmongomigration.properties="./src/main/resources/mongo/mongo.properties,./src/main/resources/mongo/migrationSet.mongo"

### How to execute mongo migration goal

 In case there are configured migration properties in pom.xml, run command:

    mvn clean install mongomigration:migrate

 In case there is need to pass parameters from command line, run the command

    mvn clean install mongomigration:migrate -Dmongomigration.properties="./src/main/resources/mongo/mongo.properties,./src/main/resources/mongo/migrationSet.mongo"
