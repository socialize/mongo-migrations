package com.sharethis.mongodb.connection;

import com.mongodb.MongoOptions;

public class MongoConnectionSettings {

    private String hostname;
    private Integer port;
    private String database;
    private String userName;
    private String password;

    private MongoOptions options;

    public MongoConnectionSettings() {
    }

    public MongoConnectionSettings(String hostname, Integer port, String database, String userName, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.userName = userName;
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public Integer getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public MongoOptions getOptions() {
        return options;
    }

    public void setOptions(MongoOptions options) {
        this.options = options;
    }

}
