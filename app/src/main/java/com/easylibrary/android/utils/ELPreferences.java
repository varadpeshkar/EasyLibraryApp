package com.easylibrary.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rohan on 26/2/17.
 */

public final class ELPreferences {

    private static final String PREFERENCE_NAME = "el_preferences";

    private static ELPreferences instance;

    private SharedPreferences mSharedPreferences;

    public static ELPreferences get(final Context context) {
        if (instance == null) {
            instance = new ELPreferences(context);
        }
        return instance;
    }

    private ELPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    public void saveString(final String key, final String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveInt(final String key, final int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void saveLong(final String key, final long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void saveBoolean(final String key, final boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void saveFloat(final String key, final float value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getString(final String key) {
        return mSharedPreferences.getString(key, null);
    }

    public long getLong(final String key) {
        return mSharedPreferences.getLong(key, 0L);
    }

    public int getInt(final String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public double getFloat(final String key) {
        return mSharedPreferences.getFloat(key, 0.0F);
    }

    public boolean getBoolean(final String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public final class Keys {
        public static final String SERVER_BASE_URL = "el.server.base.url";
        public static final String IS_LOGGED_IN = "el.is.logged.in";
        public static final String USER_EMAIL_ID = "el.user.email";
        public static final String AUTH_TOKEN = "el.auth.token";
    }
}

