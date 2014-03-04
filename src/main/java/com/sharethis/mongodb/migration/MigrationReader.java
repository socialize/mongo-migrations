package com.sharethis.mongodb.migration;

import com.sharethis.mongodb.exception.ChangeSetNotFoundException;
import com.sharethis.mongodb.file.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MigrationReader {

    @Autowired
    FileReader fileReader;

    public List<String> getMigrationNames(String pathToFile) throws ChangeSetNotFoundException {
        return fileReader.getFileAsLines(pathToFile);
    }

    public List<String> findNotApplied(List<String> available, List<String> applied) {

        List<String> copy = new ArrayList<>(available);
        copy.removeAll(applied);

        return copy;
    }
}
