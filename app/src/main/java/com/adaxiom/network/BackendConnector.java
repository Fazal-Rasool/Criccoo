package com.adaxiom.network;


import com.adaxiom.model.ModelJobList;

import java.util.List;

import rx.Observable;


public interface BackendConnector {

    void setupConnector(String apiEndPointLive, String apiEndPointStaging);

    GeneralApis getGeneralDownloader();

    interface GeneralApis{
        Observable<List<ModelJobList>> getAllJobs(int userId);
    }
}
