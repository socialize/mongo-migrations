package com.sharethis.mongodb.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nbarabash on 2/14/14.
 */
public class FileUtil {

    public static String getFileAsString(String pathToFile) throws FileNotFoundException {
        return new Scanner(new File(pathToFile), "UTF-8").useDelimiter("\\Z").next();
    }

    public static List<String> getFileAsLines(String pathToFile) throws FileNotFoundException {
        List<String> changeSet = new LinkedList<>();
        Scanner scanner = new Scanner(new File(pathToFile), "UTF-8");
        while (scanner.hasNext()) {
            changeSet.add(scanner.nextLine());
        }
        return changeSet;
    }
}
