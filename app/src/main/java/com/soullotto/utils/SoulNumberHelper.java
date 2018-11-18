package com.soullotto.utils;

import android.app.Activity;

import com.soullotto.commons.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SoulNumberHelper {
    public static Date getSoulNumberDate(Activity activity) {
        String dateString = activity.getPreferences(Activity.MODE_PRIVATE).getString(Constants.PARAM_USER_BIRTHDAY, "");

        if (dateString.isEmpty()) {
            return new Date();
        } else {
            DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.KOREA);
            try {
                return  df.parse(dateString);
            } catch (ParseException e) {
                return new Date();
            }
        }
    }
}
