package com.easylibrary.android.utils;

import android.content.Context;
import android.widget.Toast;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.BookStatus;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rohan on 12/2/17.
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

    public static BookStatus getStatus(Date expiryDate) {

        DateTime expiryJoda = new DateTime(expiryDate);
        DateTime currentJoda = new DateTime();

        int days = Days.daysBetween(currentJoda, expiryJoda).getDays();


        if (days == 0) {
            return new BookStatus(R.color.colorAccent, "Expiring today, renew soon.");
        } else if (days > 0) {
            return new BookStatus(R.color.colorGreen, days + " day(s) left to expiry.");
        } else {
            return new BookStatus(R.color.colorRed, "Expired since " + Math.abs(days) + " day(s), renew or return to avoid fine");
        }
    }
}
