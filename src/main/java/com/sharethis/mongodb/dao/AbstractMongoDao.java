package com.sharethis.mongodb.dao;

import com.mongodb.DB;

public abstract class AbstractMongoDao {

    protected DB db;

    public void setDb(DB db) {
        this.db = db;
    }

}
