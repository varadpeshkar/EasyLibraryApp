package com.easylibrary.android.api.network.services;

import com.easylibrary.android.api.models.Student;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by rohan on 22/3/17.
 */

public interface StudentAPIService {

    @GET("/EasyLibrary/students/getProfile")
    Observable<Student> getProfile();
}
