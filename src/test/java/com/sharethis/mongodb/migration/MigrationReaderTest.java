package com.sharethis.mongodb.migration;

import com.sharethis.mongodb.util.PropertyHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class MigrationReaderTest {
    @Autowired
    MigrationReader reader;

    List<String> availableNames;
    List<String> appliedNames;
    List<String> diffNames;

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();
    private File migrationsSet;

    @Before
    public void setup() throws Exception {

        availableNames = new ArrayList<>();
        availableNames.add("name1.mongo");
        availableNames.add("name2.mongo");
        availableNames.add("name3.mongo");

        appliedNames = new ArrayList<>();
        appliedNames.add("name1.mongo");

        diffNames = new ArrayList<>();
        diffNames.add("name2.mongo");
        diffNames.add("name3.mongo");

        migrationsSet = tmpFolder.newFile("file");
        PropertyHelper.prepareChangeSet(migrationsSet);
    }

    @Test
    public void testGetMigrationNames() throws Exception {
        Assert.assertEquals(availableNames, reader.getMigrationNames(migrationsSet.getAbsolutePath()));
    }

    @Test
    public void test() throws Exception {
        Assert.assertEquals(diffNames, reader.findNotApplied(availableNames, appliedNames));
    }
}
