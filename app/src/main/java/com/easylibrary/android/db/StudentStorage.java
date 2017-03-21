package com.easylibrary.android.db;

import com.easylibrary.android.api.models.Student;

import io.realm.Realm;

/**
 * Created by rohan on 22/3/17.
 */

public class StudentStorage extends ELStorage {

    public static void save(Student student) {
        executeTransaction(realm -> realm.copyToRealmOrUpdate(student));
    }

    public static Student getStudent() {
        return Realm.getDefaultInstance().where(Student.class).findFirst();
    }

    public static String getName() {
        return getStudent().getName();
    }
}
