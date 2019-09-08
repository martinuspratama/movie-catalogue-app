package com.rmalan.app.moviecataloguealpha.settings;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.reminder.DailyReminder;
import com.rmalan.app.moviecataloguealpha.reminder.ReleaseTodayReminder;

public class SettingsActivity extends AppCompatActivity {

    Button btnChangeLanguage;
    private Switch switchDailyReminder, switchReleaseTodayReminder;
    private SettingsPreference settingsPreference;
    private DailyReminder dailyReminder;
    private ReleaseTodayReminder releaseTodayReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnChangeLanguage = findViewById(R.id.btn_change_language);
        switchDailyReminder = findViewById(R.id.switch_daily_reminder);
        switchReleaseTodayReminder = findViewById(R.id.switch_release_today);

        settingsPreference = new SettingsPreference(this);
        dailyReminder = new DailyReminder();
        releaseTodayReminder = new ReleaseTodayReminder();

        setSwitchDailyReminder();
        setSwitchReleaseTodayReminder();

        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        });

        switchDailyReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchDailyReminder.isChecked()) {
                    dailyReminder.setDailyReminder(getApplicationContext());
                    settingsPreference.setDailyReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_daily_reminder), Toast.LENGTH_SHORT).show();
                } else {
                    dailyReminder.disabledReminder(getApplicationContext());
                    settingsPreference.setDailyReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_daily_reminder), Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchReleaseTodayReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchReleaseTodayReminder.isChecked()) {
                    releaseTodayReminder.setReleaseReminder(getApplicationContext());
                    settingsPreference.setReleaseTodayReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_release_today_reminder), Toast.LENGTH_SHORT).show();
                } else {
                    releaseTodayReminder.disabledReminder(getApplicationContext());
                    settingsPreference.setReleaseTodayReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_release_today_reminder), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.settings));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setSwitchDailyReminder() {
        if (settingsPreference.getDailyReminder()) {
            switchDailyReminder.setChecked(true);
        } else {
            switchDailyReminder.setChecked(false);
        }
    }

    public void setSwitchReleaseTodayReminder() {
        if (settingsPreference.getReleaseTodayReminder()) {
            switchReleaseTodayReminder.setChecked(true);
        } else {
            switchReleaseTodayReminder.setChecked(false);
        }
    }

}
