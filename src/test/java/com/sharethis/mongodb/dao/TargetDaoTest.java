package com.sharethis.mongodb.dao;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class TargetDaoTest {

    @Mock
    DB db;

    @Mock
    DBCollection collection;

    @Mock
    TargetDao mockedDao;
    @Autowired
    TargetDao dao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dao.setDb(db);
    }

    @Test
    public void testExecuteScript() {
        dao.executeScript(any(String.class));
        verify(db).eval(any(String.class));
    }
}
