package com.soullotto.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soullotto.commons.Constants;
import com.soullotto.soulnumber.LottoNumbers;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SoulNumberHelper {

    public static void saveLottoNumber(Context context, LottoNumbers lottoNumbers) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lottoNumbers);
        prefsEditor.putString(Constants.PARAM_LOTTO_LIST, json);
        prefsEditor.commit();
    }

    public static LottoNumbers getLottoNumber(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString(Constants.PARAM_LOTTO_LIST, "");

        return gson.fromJson(json, LottoNumbers.class);
    }

    public static void saveExceptNumber(Context context, List<Integer> exceptNumbers) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(exceptNumbers);
        prefsEditor.putString(Constants.PARAM_EXCEPT_LIST, json);
        prefsEditor.commit();
    }

    public static List<Integer> getExceptNumber(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString(Constants.PARAM_EXCEPT_LIST, "");

        List<Integer> list = gson.fromJson(json, new TypeToken<List<Integer>>(){}.getType());

        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }


    public static void saveIncludeNumber(Context context, List<Integer> includeNumbers) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(includeNumbers);
        prefsEditor.putString(Constants.PARAM_INCLUDE_LIST, json);
        prefsEditor.commit();
    }

    public static List<Integer> getIncludeNumber(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString(Constants.PARAM_INCLUDE_LIST, "");

        List<Integer> list = gson.fromJson(json, new TypeToken<List<Integer>>(){}.getType());

        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

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
            SecureRandom r = new SecureRandom();
            int todayNumber = (r.nextInt(44) + 1);
            SoulNumberHelper.saveTodayNumber(activity, todayNumber);
            saveTodayTime(activity, new Date());

            return false;
        } else {
            return false;
        }
    }
}
