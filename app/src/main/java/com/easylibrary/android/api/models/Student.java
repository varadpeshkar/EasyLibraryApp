package com.easylibrary.android.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rohan on 22/3/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student extends RealmObject {

    @PrimaryKey
    private int id;

    private String name;

    @JsonProperty("current_year")
    private String currentYear;

    private String branch;

    @JsonProperty("mobile_number")
    private long mobile;

    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
