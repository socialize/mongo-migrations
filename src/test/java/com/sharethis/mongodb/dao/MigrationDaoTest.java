package com.sharethis.mongodb.dao;

import com.mongodb.*;
import com.sharethis.mongodb.migration.MigrationModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MigrationDaoTest {

    @Mock
    DB db;

    @Mock
    DBCollection collection;

    @Mock
    MigrationDao mockedDao;

    DBObject obj;

    private MigrationModel model;

    MigrationDao dao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dao = new MigrationDao(db);

        Date date = new Date(946677600000l);  //01/01/2000 00:00:00 UTC
        model = new MigrationModel(date, "foo", "blah");

        obj = BasicDBObjectBuilder.start()
                .add("Name", "foo")
                .add("Body", "blah")
                .add("AppliedDate", date)
                .get();

        setupMockedDao();
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
        Assert.assertEquals(new ArrayList<String>(), mockedDao.getAppliedChangesNames(collection));
    }

    private void setupMockedDao() {
        when(mockedDao.getAppliedChangesNames(collection)).thenReturn(new ArrayList<String>());
    }

}
