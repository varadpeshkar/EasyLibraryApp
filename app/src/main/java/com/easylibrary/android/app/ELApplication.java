package com.easylibrary.android.app;

import android.app.Application;

import com.easylibrary.android.db.ELRealm;

/**
 * Created by varad on 12/2/17.
 */

public class ELApplication extends Application {

    private static ELApplication instance;

    public ELApplication() {
        instance = this;
    }

    public static ELApplication getInstance() {
        if (instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        ELRealm.getInstance(this).setUpDB();
    }
}
