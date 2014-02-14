package com.sharethis.mongodb.change;

import com.sharethis.mongodb.file.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nbarabash on 2/13/14.
 */
public class ChangeSetReader {

    public static List<String> getChangeSet(String pathToFile) throws FileNotFoundException {
        return FileUtil.getFileAsLines(pathToFile);
    }
}
