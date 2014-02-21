package com.sharethis.mongodb.dao;

import com.mongodb.*;
import com.sharethis.mongodb.migration.MigrationModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class MigrationDaoTest {

    @Mock
    DB db;

    @Mock
    DBCollection collection;

    @Mock
    MigrationDao mockedDao;

    DBObject obj;

    private MigrationModel model;

    @Autowired
    MigrationDao dao;

    DBCollection dbCollection;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        dao.setDb(db);

        Date date = new Date(946677600000l);  //01/01/2000 00:00:00 UTC
        model = new MigrationModel(date, "foo", "blah");

        obj = BasicDBObjectBuilder.start()
                .add("Name", "foo")
                .add("Body", "blah")
                .add("AppliedDate", date)
                .get();

        BasicDBObject ref = new BasicDBObject();
        BasicDBObject name = new BasicDBObject("Name", 1);

        dbCollection = PowerMockito.mock(DBCollection.class);
        DBCursor dbCursor = PowerMockito.mock(DBCursor.class);
        DBObject dbObject = PowerMockito.mock(DBObject.class);

        PowerMockito.doReturn(dbCursor).when(dbCollection).find(ref, name);

        PowerMockito.doReturn(true).doReturn(true)
                .doReturn(true)
                .doReturn(false)
                .when(dbCursor).hasNext();

        PowerMockito.doReturn(dbObject)
                .doReturn(dbObject)
                .doReturn(dbObject)
                .when(dbCursor).next();

        PowerMockito.doReturn("name1")
                .doReturn("name2")
                .doReturn("name3")
                .when(dbObject).get("Name");

    }

    @Test
    public void testCreateOrUpdateCollection() {
        dao.createOrUpdateCollection("collection");
        verify(db).createCollection("collection", null);
    }

    @Test
    public void testAddAppliedChanges() {
        dao.addAppliedChanges(collection, model);
        verify(collection).save(obj);
    }

    @Test
    public void testGetAppliedChangesNames() {
        List<String> names = new ArrayList<>();
        names.add("name1");
        names.add("name2");
        names.add("name3");
        Assert.assertEquals(names, dao.getAppliedChangesNames(dbCollection));
    }
}
