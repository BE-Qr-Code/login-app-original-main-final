package com.example.loginapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ConcurrentModificationException;

public class SharedPref {

    //Storage File
    public static final String SHARED_PREF_NAME="attendance";

    //Username
    public static final String MOODLE_ID="moodleId";    //key

    public static SharedPref mInstance;
    public static Context mCtx;

    public SharedPref(Context context){
        mCtx=context;
    }

    public static synchronized SharedPref getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }

    //method to store user data
    public void storeMoodleId(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //MODE_PRIVATE: By setting this mode, the file can only be accessed using calling application
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOODLE_ID, id);    //(key, value) pair
        editor.commit();
    }

    //check if user is logged in
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MOODLE_ID, null)!=null;
    }

    //find logged in user
    public String LoggedInUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MOODLE_ID, null);
    }

    //Logout user
    public void logout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }
}
