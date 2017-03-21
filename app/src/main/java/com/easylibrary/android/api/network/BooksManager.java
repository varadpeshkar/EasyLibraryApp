package com.easylibrary.android.api.network;

import com.easylibrary.android.api.models.Book;
import com.easylibrary.android.app.ELApplication;
import com.easylibrary.android.utils.ELPreferences;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by rohan on 19/3/17.
 */

public class BooksManager {

    private static BooksManager instance;

    private ELPreferences mELPreferences;

    private BooksAPIService mBooksAPIService;

    public static BooksManager getInstance() {
        if (instance == null) {
            instance = new BooksManager();
        }
        return instance;
    }

    private BooksManager() {
        mELPreferences = ELPreferences.get(ELApplication.getInstance().getApplicationContext());
    }

    public Observable<ArrayList<Book>> searchBooks(String key) {
        mBooksAPIService = ELRetrofit.getInstance()
                .createServiceWithoutRoot(BooksAPIService.class,
                        AuthManager.getInstance().getAuthToken(),
                        AuthManager.getInstance().getEmail());

        return mBooksAPIService.searchBooks(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
