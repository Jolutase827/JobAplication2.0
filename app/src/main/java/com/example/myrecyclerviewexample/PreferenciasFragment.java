package com.example.myrecyclerviewexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class PreferenciasFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferencias,rootKey);
        SwitchPreference urlOBytes = getPreferenceManager().findPreference("url");
        urlOBytes.setSwitchTextOff("DESCARGA DE BYTES");
        urlOBytes.setSwitchTextOn("URL");

    }
}
