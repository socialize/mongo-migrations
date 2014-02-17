package com.sharethis.mongodb.file;

import com.sharethis.mongodb.exception.ChangeSetNotFoundException;
import com.sharethis.mongodb.exception.MigrationScriptNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public String getFileAsString(String pathToFile) throws MigrationScriptNotFoundException {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(pathToFile), "UTF-8");
        } catch (FileNotFoundException fnfex) {
            throw new MigrationScriptNotFoundException(pathToFile);
        }
        return scanner.useDelimiter("\\Z").next();
    }

    public List<String> getFileAsLines(String pathToFile) throws ChangeSetNotFoundException {
        List<String> changeSet = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(new File(pathToFile), "UTF-8");
            while (scanner.hasNext()) {
                changeSet.add(scanner.nextLine());
            }
        } catch (FileNotFoundException fnfex) {
            throw new ChangeSetNotFoundException(pathToFile);
        }
        return changeSet;
    }
}
