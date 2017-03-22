package com.easylibrary.android.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by varad on 12/2/17.
 */

public class ELUtility {

    public static String getFormattedLocation(String section, String shelf, int row, int column) {
        return section + "/" + shelf + "/" + row + "/" + column;
    }

    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
