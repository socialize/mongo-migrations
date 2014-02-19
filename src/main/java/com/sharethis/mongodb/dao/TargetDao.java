package com.sharethis.mongodb.dao;

import org.springframework.stereotype.Component;

@Component
public class TargetDao extends AbstractMongoDao {

    public TargetDao() {
    }

    public void executeScript(String script) {
        db.eval(script);
    }
}
