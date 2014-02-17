package com.sharethis.mongodb.dao;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class TargetDaoTest {

    @Mock
    DB db;

    @Mock
    DBCollection collection;

    @Mock
    TargetDao mockedDao;

    TargetDao dao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dao = new TargetDao(db);
    }

    @Test
    public void testExecuteScript() {
        dao.executeScript(any(String.class));
        verify(db).eval(any(String.class));
    }
}
