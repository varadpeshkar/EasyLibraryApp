package com.easylibrary.android.api.network;

import com.easylibrary.android.api.models.Auth;
import com.easylibrary.android.api.models.UserCredentials;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by rohan on 26/2/17.
 */

public interface AuthAPIService {

    @POST("/EasyLibrary/students/authenticate")
    Observable<Auth> getAuthToken(@Body UserCredentials userCredentials);
}
