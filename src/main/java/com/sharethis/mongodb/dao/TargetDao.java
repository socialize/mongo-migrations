package com.sharethis.mongodb.dao;

import com.mongodb.DB;

public class TargetDao extends AbstractMongoDao {

    public TargetDao(DB targetDB) {
        this.db = targetDB;
    }

    public void executeScript(String script) {
        db.eval(script);
    }
}
