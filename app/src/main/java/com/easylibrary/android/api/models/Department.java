package com.easylibrary.android.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rohan on 21/3/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Department extends RealmObject {

    @PrimaryKey
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
