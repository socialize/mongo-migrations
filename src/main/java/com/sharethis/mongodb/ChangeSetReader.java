package com.sharethis.mongodb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nbarabash on 2/13/14.
 */
public class ChangeSetReader {

    public static List<String> getChangeSet(String fileName) throws FileNotFoundException {
        List<String> changeSet = new LinkedList<>();
        Scanner scanner = new Scanner( new File(fileName), "UTF-8" );
        while(scanner.hasNext()){
            changeSet.add(scanner.nextLine());
        }
        return changeSet;
    }
}
