package com.easylibrary.android.db;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by varad on 14/2/17.
 */

public class ELStorage {

    /**
     * Realm execute transaction method
     *
     * @param transaction - Realm Transaction
     */
    protected static void executeTransactionAsync(@NonNull Realm.Transaction transaction,
                                                  Realm.Transaction.Callback callback) {
        Realm realm;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(transaction, callback::onSuccess);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    protected static void executeTransaction(@NonNull Realm.Transaction transaction) {
        Realm realm;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(transaction);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * Closing realm default instance
     */
    public static void close() {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.close();
        }
    }

    /**
     * Save method common to all RealmObjects
     *
     * @param realmObject - RealmObject to save
     */
    public static void save(RealmObject realmObject) {
        executeTransaction(realm -> realm.copyToRealmOrUpdate(realmObject));
    }
}
