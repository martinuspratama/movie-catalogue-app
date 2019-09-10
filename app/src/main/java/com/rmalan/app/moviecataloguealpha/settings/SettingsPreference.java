package com.rmalan.app.moviecataloguealpha.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsPreference {

    private static final String PREFS_NAME = "settings_pref";

    private static final String DAILY_REMINDER = "daily_reminder";
    private static final String RELEASE_TODAY_REMINDER = "release_today_reminder";

    private final SharedPreferences preferences;

    SettingsPreference(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean getDailyReminder() {
        return preferences.getBoolean(DAILY_REMINDER, false);
    }

    public void setDailyReminder(boolean isActive) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(DAILY_REMINDER, isActive);
        editor.apply();
    }

    public boolean getReleaseTodayReminder() {
        return preferences.getBoolean(RELEASE_TODAY_REMINDER, false);
    }

    public void setReleaseTodayReminder(boolean isActive) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(RELEASE_TODAY_REMINDER, isActive);
        editor.apply();
    }

}