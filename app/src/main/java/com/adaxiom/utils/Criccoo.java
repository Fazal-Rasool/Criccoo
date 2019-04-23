package com.adaxiom.utils;

import android.app.Application;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.network.BackendConnector;
import com.adaxiom.network.RetrofitConnector;

import static com.adaxiom.utils.Constants.URL_LIVE;
import static com.adaxiom.utils.Constants.URL_STAGING;

public class Criccoo extends Application {

    BackendConnector connector;
    DownloaderManager downloaderManager;

    @Override
    public void onCreate() {
        super.onCreate();

        connector = new RetrofitConnector();
        connector.setupConnector(URL_LIVE, URL_STAGING);

        downloaderManager = new DownloaderManager(connector);



    }
}
