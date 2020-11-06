package com.example.e_hrsystem.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.e_hrsystem.model.User;
import com.google.gson.Gson;

public class SharedPreferencesHelper {

    private static String KEY_USER_OBJECT = "KEY_USER_OBJECT";


    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences("my_data", Context.MODE_PRIVATE);
    }

    @SuppressLint("ApplySharedPref")
    public static void saveUser(Context context, User user) {
        // Saving the user in the SP
        String userJson = new Gson().toJson(user);
        getSharedPreference(context).edit().putString(KEY_USER_OBJECT, userJson).commit();
    }

    public static User getUser(Context context) {
        // Retrieve the user from the sp
        String userJson = getSharedPreference(context).getString(KEY_USER_OBJECT, "");
        if (userJson != null && !userJson.isEmpty()) {
            return new Gson().fromJson(userJson, User.class);
        }
        return null;
    }

    public static String getSavedName(Context context) {
        return getUser(context).getUsername();
    }

    @SuppressLint("ApplySharedPref")
    public static void logout(Context context) {
        getSharedPreference(context).edit().clear().commit();
    }

    public static boolean isUserLoggedIn(Context context) {
        return getUser(context) != null;
    }


}
