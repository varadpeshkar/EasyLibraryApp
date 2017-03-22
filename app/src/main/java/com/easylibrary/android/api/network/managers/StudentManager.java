package com.easylibrary.android.api.network.managers;

import com.easylibrary.android.api.models.BookIssueRequest;
import com.easylibrary.android.api.models.Student;
import com.easylibrary.android.api.network.ELRetrofit;
import com.easylibrary.android.api.network.services.StudentAPIService;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rohan on 22/3/17.
 */

public class StudentManager {

    private static StudentManager instance;

    private StudentAPIService mStudentAPIService;

    public static StudentManager getInstance() {
        if (instance == null) {
            instance = new StudentManager();
        }
        return instance;
    }

    private StudentManager() {

    }

    public Observable<Student> getProfile() {
        mStudentAPIService = ELRetrofit.getInstance()
                .createServiceWithoutRoot(StudentAPIService.class,
                        AuthManager.getInstance().getAuthToken(),
                        AuthManager.getInstance().getEmail());

        return mStudentAPIService.getProfile()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BookIssueRequest> requestBookIssue(BookIssueRequest bookIssueRequest) {
        mStudentAPIService = ELRetrofit.getInstance()
                .createServiceWithoutRoot(StudentAPIService.class,
                        AuthManager.getInstance().getAuthToken(),
                        AuthManager.getInstance().getEmail());

        return mStudentAPIService.requestBookIssue(bookIssueRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ArrayList<BookIssueRequest>> getAllIssueRequests() {
        mStudentAPIService = ELRetrofit.getInstance()
                .createServiceWithoutRoot(StudentAPIService.class,
                        AuthManager.getInstance().getAuthToken(),
                        AuthManager.getInstance().getEmail());

        return mStudentAPIService.getAllIssueRequests()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
