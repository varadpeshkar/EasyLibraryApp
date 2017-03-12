package com.easylibrary.android.utils;

/**
 * Created by varad on 12/2/17.
 */

public class ELUtility {

    public static String getFormattedLocation(String section, String shelf, int row, int column) {
        return section + "/" + shelf + "/" + row + "/" + column;
    }
}
