package com.sharethis.mongodb.change;

import java.util.ArrayList;
import java.util.List;


public class ChangeSetUtil {

    public static List<String> findNotApplied(List<String> available, List<String> applied) {

        List<String> copy = new ArrayList<>(available);
        copy.removeAll(applied);

        return copy;
    }

}
