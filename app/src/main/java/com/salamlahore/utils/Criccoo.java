package com.salamlahore.utils;

import android.app.Application;
import android.content.ContextWrapper;

import com.salamlahore.manager.DownloaderManager;
import com.salamlahore.network.BackendConnector;
import com.salamlahore.network.RetrofitConnector;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.pixplicity.easyprefs.library.Prefs;


import io.fabric.sdk.android.Fabric;

import static com.salamlahore.utils.Constants.URL_LIVE;
import static com.salamlahore.utils.Constants.URL_STAGING;

public class Criccoo extends Application {

    BackendConnector connector;
    DownloaderManager downloaderManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
//        Crashlytics.getInstance().crash();

        Stetho.initializeWithDefaults(this);

        //EasyPreferences Library
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();


        connector = new RetrofitConnector();
        connector.setupConnector(URL_LIVE, URL_STAGING);

        downloaderManager = new DownloaderManager(connector);



    }
}
