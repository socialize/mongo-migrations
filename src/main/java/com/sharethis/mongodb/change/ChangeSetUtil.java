package com.sharethis.mongodb.change;

import ch.qos.logback.core.util.StringCollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nbarabash on 2/14/14.
 */
public class ChangeSetUtil {

    public static List<String> findNotApplied(List<String> available, List<String> applied) {

        List<String> copy = new ArrayList<>(available);
        copy.removeAll(applied);

        return copy;
    }

}
