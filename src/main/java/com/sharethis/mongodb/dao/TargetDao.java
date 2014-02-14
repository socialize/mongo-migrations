package com.sharethis.mongodb.dao;

import com.mongodb.DB;

/**
 * Created by nbarabash on 2/14/14.
 */
public class TargetDao extends AbstractMongoDao {

    public TargetDao(DB targetDB) {
        this.db = targetDB;
    }

    public void executeScript(String script) {
        db.eval(script);
    }
}
