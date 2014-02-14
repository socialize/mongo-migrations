package com.sharethis.mongodb.connection;

import com.mongodb.MongoOptions;

/**
 * Created by nbarabash on 2/13/14.
 */

/**
 * MongoConnection is a wrapper on required parameters for connecting to mongo
 */

public class MongoConnectionSettings {

    private String serverId;
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

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MongoOptions getOptions() {
        return options;
    }

    public void setOptions(MongoOptions options) {
        this.options = options;
    }

    public String getServerId() {
        return serverId;
    }
}
