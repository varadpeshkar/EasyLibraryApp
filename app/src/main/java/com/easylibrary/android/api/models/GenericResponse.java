package com.easylibrary.android.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by rohan on 20/3/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResponse {

    private boolean success;

    private int code;

    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
