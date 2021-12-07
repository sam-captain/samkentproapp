package com.samkent.proapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceStorage {

    Context context;
    SharedPreferences myFile;
    public static final String PREF_NAME = "com.angie.proapp.SHARED_PREF";
    public static final String USER_NAME = "com.angie.proapp.USER_NAME";
    public static final String USER_EMAIL = "com.angie.proapp.USER_EMAIL";
    public static final String USER_NUMBER = "com.angie.proapp.USER_NUMBER";
    public static final String USER_LOGGED_IN = "com.angie.proapp.USER_LOGGED_IN";
    public static final String USER_IMAGE = "com.angie.proapp.USER_IMAGE";
    public static final String USER_PASSWORD = "com.angie.proapp.USER_PASSWORD";
    public PreferenceStorage(Context context) {

        this.context = context;
        this.myFile = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

    }


    public String getUserName(){
        return myFile.getString(USER_NAME,"");
    }

    public void saveUserData(String name, String email, String number, String password) {
        SharedPreferences.Editor editor = myFile.edit();
        editor.putString(USER_NAME,name);
        editor.putString(USER_EMAIL,email);
        editor.putString(USER_NUMBER,number);
        editor.putString(USER_PASSWORD,password);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return myFile.getBoolean(USER_LOGGED_IN,false);

    }

    public void setUserLoggedIn(boolean status) {
        SharedPreferences.Editor editor = myFile.edit();
        editor.putBoolean(USER_LOGGED_IN, status);
        editor.apply();
    }

    public Boolean authenticate(String name, String password) {
        String current_name = myFile.getString(USER_NAME,"");
        String current_password = myFile.getString(USER_PASSWORD,"");

        if (current_name.contentEquals(name)&& current_password.contentEquals(password)){
            return true;
        }
        else {
            return false;
        }
    }
}
