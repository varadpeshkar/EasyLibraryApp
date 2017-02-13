package com.easylibrary.android.db;

import com.easylibrary.android.api.models.Book;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rohan on 14/2/17.
 */

public class BookStorage extends ELStorage {

    public static void save(Book book) {
        executeTransaction(realm -> realm.copyToRealmOrUpdate(book));
    }

    public static RealmResults<Book> getAll() {
        return Realm.getDefaultInstance().where(Book.class).findAll();
    }


}
