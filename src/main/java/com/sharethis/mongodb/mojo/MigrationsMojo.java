package com.sharethis.mongodb.mojo;

import com.sharethis.mongodb.migration.MigrationManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugin.AbstractMojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Mojo(name = "migrate")
public class MigrationsMojo extends AbstractMojo {

    private static Logger log = LoggerFactory.getLogger(MigrationsMojo.class);

    @Parameter(property = "mongomigration.properties", required = true)
    private String[] args;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        MigrationManager migrationManager = (MigrationManager) context.getBean("migrationManager");
        try {
            migrationManager.migrate(args);
        } catch (Exception e) {
            log.error("Error during mongo migration", e);
        }
    }
}
