package com.easylibrary.android.api.network;

import com.easylibrary.android.api.models.Auth;
import com.easylibrary.android.api.models.GenericResponse;
import com.easylibrary.android.api.models.UserCredentials;
import com.easylibrary.android.app.ELApplication;
import com.easylibrary.android.utils.ELPreferences;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rohan on 26/2/17.
 */

public class AuthManager {


    private static AuthManager instance;

    private ELPreferences mELPreferences;

    private AuthAPIService mAuthAPIService;

    public static AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    private AuthManager() {
        mELPreferences = ELPreferences.get(ELApplication.getInstance().getApplicationContext());
    }

    public Observable<Auth> loginUser(String email, String password) {
        mAuthAPIService = ELRetrofit.getInstance().createServiceWithoutRoot(AuthAPIService.class);
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail(email);
        userCredentials.setPassword(password);
        return mAuthAPIService.getAuthToken(userCredentials)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GenericResponse> getStatus() {
        mAuthAPIService = ELRetrofit.getInstance().createServiceWithoutRoot(AuthAPIService.class);
        return mAuthAPIService.getStatus()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void addAuthentication(Auth auth) {
        mELPreferences.saveString(ELPreferences.Keys.AUTH_TOKEN, auth.getAuthToken());
    }

    public void addEmail(String email) {
        mELPreferences.saveString(ELPreferences.Keys.USER_EMAIL_ID, email);
    }

    public String getAuthToken() {
        return mELPreferences.getString(ELPreferences.Keys.AUTH_TOKEN);
    }

    public String getEmail() {
        return mELPreferences.getString(ELPreferences.Keys.USER_EMAIL_ID);
    }

}
