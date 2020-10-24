package com.example.e_hrsystem.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static String KEY_NAME = "name";

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences("my_data", Context.MODE_PRIVATE);
    }

    @SuppressLint("ApplySharedPref")
    public static void saveName(Context context, String name) {
        // This code is for testing only
        SharedPreferences sharedPreferences = getSharedPreference(context);
        sharedPreferences.edit()
                .putString(KEY_NAME, name)
                .commit();
    }

    public static String getSavedName(Context context) {
        SharedPreferences sharedPreferences = getSharedPreference(context);
        return sharedPreferences.getString(KEY_NAME, "");
    }

    @SuppressLint("ApplySharedPref")
    public static void logout(Context context) {
        getSharedPreference(context).edit().clear().commit();
    }

    public static boolean isUserLoggedIn(Context context) {
        return !getSavedName(context).isEmpty();
    }


}
