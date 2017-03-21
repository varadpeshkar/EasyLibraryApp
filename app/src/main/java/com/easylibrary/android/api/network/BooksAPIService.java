package com.easylibrary.android.api.network;

import com.easylibrary.android.api.models.Book;

import java.util.ArrayList;

import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rohan on 19/3/17.
 */

public interface BooksAPIService {

    @POST("/EasyLibrary/students/searchbook/{key}")
    Observable<ArrayList<Book>> searchBooks(@Path("key") String searchKey);

}
