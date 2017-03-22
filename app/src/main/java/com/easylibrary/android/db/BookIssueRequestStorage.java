package com.easylibrary.android.db;

import com.easylibrary.android.api.models.BookIssueRequest;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rohan on 22/3/17.
 */

public class BookIssueRequestStorage extends ELStorage {

    public static void save(BookIssueRequest bookIssueRequest) {
        executeTransaction(realm -> realm.copyToRealmOrUpdate(bookIssueRequest));
    }

    public static void save(ArrayList<BookIssueRequest> bookIssueRequests) {
        executeTransaction(realm -> realm.copyToRealmOrUpdate(bookIssueRequests));
    }

    public static boolean isBookRequested(int bookId) {
        return Realm.getDefaultInstance().where(BookIssueRequest.class)
                .equalTo("bookId", bookId).findAll().size() > 0;
    }

    public static String getBookRequestStatus(int bookId) {
        return Realm.getDefaultInstance().where(BookIssueRequest.class)
                .equalTo("bookId", bookId).findFirst().getStatus();
    }

    public static RealmResults<BookIssueRequest> getAllPending() {
        return Realm.getDefaultInstance().where(BookIssueRequest.class)
                .notEqualTo("status", "Approved").findAll();
    }

    public static RealmResults<BookIssueRequest> getAllApproved() {
        return Realm.getDefaultInstance().where(BookIssueRequest.class)
                .equalTo("status", "Approved").findAll();
    }


}
