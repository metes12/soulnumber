package com.soullotto.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.soullotto.commons.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SoulNumberHelper {

    public static void saveTodayNumber(Activity activity, int todayNumber) {
        SharedPreferences sp = activity.getPreferences(Activity.MODE_PRIVATE);
        sp.edit().putInt(Constants.PARAM_TODAY_NUMBER, todayNumber).apply();
    }

    public static int getTodayNumber(Activity activity) {
        SharedPreferences sp = activity.getPreferences(Activity.MODE_PRIVATE);
        int todayNumber = sp.getInt(Constants.PARAM_TODAY_NUMBER, 1);

        return todayNumber;
    }

    public static boolean isNewDate(Activity activity, Date currentTime) {
        SharedPreferences sp = activity.getPreferences(Activity.MODE_PRIVATE);
        String dateString = sp.getString(Constants.PARAM_TODAY_TIME, "");

        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.KOREA);
        Date lastDate;
        if (dateString.isEmpty()) {
            sp.edit().putString(Constants.PARAM_TODAY_TIME, df.toString()).apply();
            lastDate = new Date();
        } else {
            try {
                lastDate = df.parse(dateString);
            } catch (ParseException e) {
                lastDate = new Date();
            }
        }

        if (currentTime.after(lastDate) && currentTime.getDate() != lastDate.getDate()) {
            sp.edit().putString(Constants.PARAM_TODAY_TIME, df.toString()).apply();
            return true;
        } else {
            return false;
        }
    }
}
