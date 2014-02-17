package com.sharethis.mongodb.file;

import com.sharethis.mongodb.exception.ChangeSetNotFoundException;
import com.sharethis.mongodb.exception.MigrationScriptNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class FileReaderTest {

    public static final String NO_FILE = "no_file";
    private FileReader reader = new FileReader();
    @Mock
    private FileReader mockedReader;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mockedReader.getFileAsString(any(String.class))).thenReturn("");

        when(mockedReader.getFileAsLines(any(String.class))).thenReturn(new ArrayList<String>());
    }

    @Test(expected = MigrationScriptNotFoundException.class)
    public void testGetFileAsStringException() throws Exception{
        reader.getFileAsString(NO_FILE);
    }

    @Test
    public void testGetFileAsString() throws Exception{
        Assert.assertEquals("", mockedReader.getFileAsString(any(String.class)));
    }

    @Test(expected = ChangeSetNotFoundException.class)
    public void testGetFileAsLinesException() throws Exception {
        reader.getFileAsLines(NO_FILE);
    }

    @Test
    public void testGetFileAsLines() throws Exception {
        Assert.assertEquals(new ArrayList<String>(), mockedReader.getFileAsLines(NO_FILE));
    }
}
