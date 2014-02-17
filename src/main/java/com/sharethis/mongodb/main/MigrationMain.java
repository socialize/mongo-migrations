package com.sharethis.mongodb.main;

import com.sharethis.mongodb.migration.MigrationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MigrationMain {

    private static Logger log = LoggerFactory.getLogger(MigrationMain.class);

    public static void main(String[] args) throws Exception {
        try {
            new MigrationManager().migrate(args);
        } catch (Exception ex) {
            log.error("Migration failed!", ex);
            log.error("Application will close");
        }
    }
}
