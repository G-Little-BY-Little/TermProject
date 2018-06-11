package org.androidtown.sina_ver01;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static SharedPreferences mSharedPrefs;
    private static SharedPreferences.Editor mEditor;

    private static final String USER_NAME = "user_name";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_IS_FIRST = "user_is_first";

    private SharedPrefManager(Context context) {
        try {
            mSharedPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
            mEditor = mSharedPrefs.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void setUserName(String name) {
        mEditor.putString(USER_NAME, name);
        mEditor.commit();
    }

    public String getUserName() {
        return mSharedPrefs.getString(USER_NAME, null);
    }

    public void setUserPassword(String password) {
        mEditor.putString(USER_PASSWORD, password);
        mEditor.commit();
    }

    public String getUserPassword() {
        return mSharedPrefs.getString(USER_PASSWORD, null);
    }

    public void setUserIsFirst(String isFirst) {
        mEditor.putString(USER_IS_FIRST, isFirst);
        mEditor.commit();
    }

    public String getUserIsFirst() {
        return mSharedPrefs.getString(USER_IS_FIRST, "0");
    }
}
