package com.harmankaya.rubrikkapp3.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.harmankaya.rubrikkapp3.utils.Config;

public class UsersPrefs
{
    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences userSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    public UsersPrefs(Context context)
    {
        this.userSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = userSharedPrefs.edit();
    }

    public String getToken()
    {
        return userSharedPrefs.getString(Config.KEY_TOKEN, "");
    }

    public void setToken(String token)
    {
        prefsEditor.putString(Config.KEY_TOKEN, token);
    }

    public String getUserFirstName()
    {
        return userSharedPrefs.getString(Config.KEY_FIRST_NAME, "");
    }

    public void setUserFirstName(String userName)
    {
        prefsEditor.putString(Config.KEY_FIRST_NAME, userName).commit();
    }

    public String getUserLastName()
    {
        return userSharedPrefs.getString(Config.KEY_LAST_NAME, "");
    }

    public void setUserLastName(String userName)
    {
        prefsEditor.putString(Config.KEY_LAST_NAME, userName);
    }

    public String getUserEmail()
    {
        return userSharedPrefs.getString(Config.KEY_EMAIL, "");
    }

    public void setUserEmail(String userEmail)
    {
        prefsEditor.putString(Config.KEY_EMAIL, userEmail).commit();
    }

    public String getUserPassword()
    {
        return userSharedPrefs.getString(Config.KEY_PASSWORD, "");
    }

    public void setUserPassword(String userPassword)
    {
        prefsEditor.putString(Config.KEY_PASSWORD, userPassword).commit();
    }

}
