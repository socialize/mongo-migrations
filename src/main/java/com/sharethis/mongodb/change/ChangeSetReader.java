package com.sharethis.mongodb.change;

import com.sharethis.mongodb.exception.ChangeSetNotFoundException;
import com.sharethis.mongodb.file.FileUtil;
import java.util.List;

public class ChangeSetReader {

    public static List<String> getChangeSet(String pathToFile) throws ChangeSetNotFoundException {
        return FileUtil.getFileAsLines(pathToFile);
    }
}
