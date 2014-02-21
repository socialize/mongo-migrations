package com.sharethis.mongodb.main;

import com.sharethis.mongodb.migration.MigrationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MigrationMain {

    private static Logger log = LoggerFactory.getLogger(MigrationMain.class);

    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        MigrationManager migrationManager = (MigrationManager) context.getBean("migrationManager");
        try {
            migrationManager.migrate(args);
        } catch (Exception e) {
            log.error("Error during mongo migration", e);
        }

    }
}
