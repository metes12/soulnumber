package com.soullotto.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.soullotto.commons.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class SoulNumberHelper {

    public static void saveBirthDay(int birthDay, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.PARAM_USER_BIRTHDAY, birthDay);
        editor.commit();
    }

    public static int getBirthDay(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.PARAM_USER_BIRTHDAY, 1);
    }

    public static void saveTodayNumber(Activity activity, int todayNumber) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.PARAM_TODAY_NUMBER, todayNumber);
        editor.commit();
    }

    public static int getTodayNumber(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        return preferences.getInt(Constants.PARAM_TODAY_NUMBER, 1);
    }

    public static void saveTodayTime(Activity activity, Date todayTime) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.PARAM_TODAY_TIME, todayTime.toString());
        editor.commit();
    }

    public static Date getTodayTime(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String todayTimeString = preferences.getString(Constants.PARAM_TODAY_TIME, "");
        if (todayTimeString.isEmpty()) {
            return null;
        } else {
            return new Date(todayTimeString);
        }
    }

    public static boolean isNewDay(Activity activity, Date currentTime) {
        Date lastDate = getTodayTime(activity);

        if (lastDate != null && currentTime.after(lastDate) && currentTime.getDate() != lastDate.getDate()) {
            saveTodayTime(activity, new Date());
            return true;
        } else if (lastDate == null) {
            Random r = new Random();
            int todayNumber = (r.nextInt(44) + 1);
            SoulNumberHelper.saveTodayNumber(activity, todayNumber);
            saveTodayTime(activity, new Date());

            return false;
        } else {
            return false;
        }
    }
}
