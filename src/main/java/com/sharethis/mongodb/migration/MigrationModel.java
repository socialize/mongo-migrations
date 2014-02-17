package com.sharethis.mongodb.migration;

import java.util.Date;

public class MigrationModel {

    private Date appliedDate;
    private String scriptName;
    private String scriptBody;

    public MigrationModel(Date appliedDate, String scriptName, String scriptBody) {
        this.appliedDate = appliedDate;
        this.scriptName = scriptName;
        this.scriptBody = scriptBody;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public String getScriptBody() {
        return scriptBody;
    }

    public String getScriptName() {
        return scriptName;
    }
}
