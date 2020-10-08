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
        prefsEditor.putString(Config.KEY_TOKEN, token).commit();
    }

    public String getId()
    {
        return userSharedPrefs.getString(Config.USER_ID, "");
    }

    public void setId(String id)
    {
        prefsEditor.putString(Config.USER_ID, id);
    }

    public String getName()
    {
        return userSharedPrefs.getString(Config.KEY_NAME, "");
    }

    public void setName(String name)
    {
        prefsEditor.putString(Config.KEY_NAME, name).commit();
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
