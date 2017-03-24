package com.easylibrary.android.api.network.services;

import com.easylibrary.android.api.models.Book;
import com.easylibrary.android.api.models.Department;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rohan on 19/3/17.
 */

public interface BooksAPIService {

    @POST("/EasyLibrary/students/searchbook/{key}")
    Observable<ArrayList<Book>> searchBooks(@Path("key") String searchKey);

    @GET("/EasyLibrary/students/getAllDepartments/")
    Observable<ArrayList<Department>> getDepartments();

    @GET("/EasyLibrary/students/getBooksByDepartment/{department}")
    Observable<ArrayList<Book>> getBookByDepartment(@Path("department") String department);

    @GET("/EasyLibrary/students/getRecommendations/")
    Observable<ArrayList<Book>> getBookRecommendations();

}
