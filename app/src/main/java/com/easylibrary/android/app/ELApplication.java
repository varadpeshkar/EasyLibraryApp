package com.easylibrary.android.app;

import android.app.Application;

import com.easylibrary.android.db.ELRealm;

/**
 * Created by rohan on 12/2/17.
 */

public class ELApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ELRealm.getInstance(this).setUpDB();
    }
}
