package com.sharethis.mongodb.migration;

import java.util.Date;

/**
 * Created by nbarabash on 2/13/14.
 */
public class MigrationModel {

    private Date appliedDate;
    private String scriptName;
    private String scriptBody;

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getScriptBody() {
        return scriptBody;
    }

    public void setScriptBody(String scriptBody) {
        this.scriptBody = scriptBody;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public MigrationModel(Date appliedDate, String scriptName, String scriptBody) {
        this.appliedDate = appliedDate;
        this.scriptName = scriptName;
        this.scriptBody = scriptBody;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MigrationModel that = (MigrationModel) o;

        if (appliedDate != null ? !appliedDate.equals(that.appliedDate) : that.appliedDate != null) return false;
        if (scriptBody != null ? !scriptBody.equals(that.scriptBody) : that.scriptBody != null) return false;
        if (scriptName != null ? !scriptName.equals(that.scriptName) : that.scriptName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appliedDate != null ? appliedDate.hashCode() : 0;
        result = 31 * result + (scriptName != null ? scriptName.hashCode() : 0);
        result = 31 * result + (scriptBody != null ? scriptBody.hashCode() : 0);
        return result;
    }
}
