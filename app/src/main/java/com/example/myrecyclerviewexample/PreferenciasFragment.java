package com.example.myrecyclerviewexample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.myrecyclerviewexample.base.CallInterface;
import com.example.myrecyclerviewexample.model.Model;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PreferenciasFragment extends PreferenceFragmentCompat {

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferencias,rootKey);
        SwitchPreference urlOBytes = getPreferenceManager().findPreference("url");
        EditTextPreference pathImag = getPreferenceManager().findPreference("pathimage");
        EditTextPreference ip = getPreferenceManager().findPreference("ip");
        EditTextPreference puerto = getPreferenceManager().findPreference("puerto");
        EditTextPreference prefijo = getPreferenceManager().findPreference("prefijo");

        if (urlOBytes.isChecked()){
            urlOBytes.setTitle("DESCARGA DE BYTES");
            pathImag.setVisible(false);
        }
        urlOBytes.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                if (urlOBytes.getTitle().equals("DESCARGA DE BYTES")) {
                    urlOBytes.setTitle("URL");
                    pathImag.setVisible(true);
                }else {
                    urlOBytes.setTitle("DESCARGA DE BYTES");
                    pathImag.setVisible(false);
                }
                return true;
            }
        });
    }






}
