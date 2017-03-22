package com.easylibrary.android.api.network.services;

import com.easylibrary.android.api.models.BookIssueRequest;
import com.easylibrary.android.api.models.Student;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by rohan on 22/3/17.
 */

public interface StudentAPIService {

    @GET("/EasyLibrary/students/getProfile")
    Observable<Student> getProfile();

    @POST("/EasyLibrary/students/requestBookIssue")
    Observable<BookIssueRequest> requestBookIssue(@Body BookIssueRequest bookIssueRequest);

    @GET("/EasyLibrary/students/getAllBookIssueRequests")
    Observable<ArrayList<BookIssueRequest>> getAllIssueRequests();

}
