package com.example.thelimiter.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

public class LimitCalculator {

    private final SharedPreferences sharedPreferences;

    public LimitCalculator(Context context, String prefsName) {
        this.sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
    }

    public float getDailyLimit(String key) {
        return sharedPreferences.getFloat(key, -1);
    }

    public boolean hasDailyLimit(String key) {
        return getDailyLimit(key) != -1;
    }

    public float calculateAndSaveLimit(String key, double totalAmount) {
        int daysInMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        float dailyLimit = (float) (totalAmount / daysInMonth);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, dailyLimit);
        editor.apply();

        return dailyLimit;
    }
}
