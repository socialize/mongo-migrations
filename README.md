mongo-migrations
================

Mongo Migrations Java Library

### Required environment
    maven, java 7

### How to build

    mvn clean package

### How to run as a too locally

    cp src/main/resources/example/mongo.properties /target
    cp -R src/main/resources/example/mongo /target
    cd target
    java  -jar mongo-migrations-1.0-SNAPSHOT.jar mongo.properties /mongo/changeSet.mongo


### How to added as plugin into project

    <plugin>
        <groupId>com.sharethis.mongo</groupId>
        <artifactId>mongomigration</artifactId>
        <version>1.0-SNAPSHOT</version>
    </plugin>

### How to pass parameters from pom.xml

     <configuration>
        <args>
            <arg>./src/main/resources/mongo/mongo.properties</arg>
            <arg>./src/main/resources/mongo/migrationSet.mongo</arg>
        </args>
     </configuration>

### How to pass parameters from command line

    -Dmongomigration.properties="./src/main/resources/mongo/mongo.properties,./src/main/resources/mongo/migrationSet.mongo"

### How to execute mongo migration goal

 In case there are configured migration properties in pom.xml, run command:

    mvn clean install mongomigration:migrate

 In case there is need to pass parameters from command line, run the command

    mvn clean install mongomigration:migrate -Dmongomigration.properties="./src/main/resources/mongo/mongo.properties,./src/main/resources/mongo/migrationSet.mongo"