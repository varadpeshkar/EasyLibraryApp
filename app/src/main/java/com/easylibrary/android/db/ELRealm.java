package com.easylibrary.android.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by rohan on 14/2/17.
 */

public class ELRealm {

    private static ELRealm instance;

    private Context mContext;

    private ELRealm(Context context) {
        mContext = context;
    }

    public static ELRealm getInstance(Context context) {
        if (instance == null) {
            instance = new ELRealm(context);
        }
        return instance;
    }

    public static ELRealm getInstance() {
        return instance;
    }

    public synchronized void setUpDB() {
        Realm.init(mContext);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static synchronized void clearDB() {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
        }
        realm.close();
    }

    public static synchronized void clearSchema() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .build();
        try {
            Realm.deleteRealm(config);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }
}
