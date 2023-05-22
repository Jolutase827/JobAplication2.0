package com.example.myrecyclerviewexample.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceManager;

public class MyPreferenceManager {

    private static MyPreferenceManager instance;
    private SharedPreferences preferences;
    private MyPreferenceManager(){
    }

    public static MyPreferenceManager getInstance(Context context){
        if (instance == null ){
            instance = new MyPreferenceManager();
            instance.inicializa(context);
        }
        return instance;
    }

    private void inicializa(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getApiUrl(){
        return "http://"+getIp()+":"+getPuerto()+"/"+getPrefijo();
    }

    private String getIp(){

        return preferences.getString("ip","10.13.0.1");
    }

    private String getPuerto(){

        return preferences.getString("puerto","8080");
    }

    private String getPrefijo(){
        return preferences.getString("prefijo","api/");
    }

    public String getPathImagenes(){
        return preferences.getString("pathimage","http:10.13.0.1/images/");
    }
    public boolean getImageOption(){
        return preferences.getBoolean("url",true);
    }
}
