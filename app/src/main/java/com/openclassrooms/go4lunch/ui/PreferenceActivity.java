package com.openclassrooms.go4lunch.ui;

import static com.openclassrooms.go4lunch.services.Notification.setupNotif;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.services.Notification;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private static final String PREFS = "PREFS_GO4LUNCH";
        private static final String PREFS_NOTIF = "PREFS_NOTIF";
        SharedPreferences sharedPreferences;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
            SwitchPreference switchNotif = findPreference("notification");
            sharedPreferences = getContext().getSharedPreferences(PREFS, MODE_PRIVATE);
            boolean statNotif = sharedPreferences.getBoolean(PREFS_NOTIF, true);
            switchNotif.setChecked(statNotif);

            switchNotif.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean switched = !((SwitchPreference) preference).isChecked();
                    sharedPreferences.edit().putBoolean(PREFS_NOTIF,switched).apply();
                    if (!switched){
                        WorkManager.getInstance(getContext()).cancelAllWorkByTag("GO4LUNCH");
                    } else {
                        setupNotif(getContext());
                    }
                    return true;
                }
            });
        }
    }
}
