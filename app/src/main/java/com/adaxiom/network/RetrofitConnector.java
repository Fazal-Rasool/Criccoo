package com.adaxiom.network;

import com.adaxiom.criccoo.BuildConfig;
import com.adaxiom.model.ModelJobList;

import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rx.Observable;

public class RetrofitConnector implements BackendConnector, BackendConnector.GeneralApis {

    private RestAdapter restAdapter;

    private String apiEndPoint_Staging, apiEndPoint_Live;
    private ApiCalls calls;
//    private Retrofit retrofit;

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Content-Type", "application/json");
        }
    };


    @Override
    public void setupConnector(String live, String staging) {
        this.apiEndPoint_Live = live;
        this.apiEndPoint_Staging = staging;

        if (BuildConfig.DEBUG)

            restAdapter = new RestAdapter.Builder().setEndpoint(apiEndPoint_Live).
                    setRequestInterceptor(requestInterceptor).setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

//            retrofit = new Retrofit.Builder()
//                    .baseUrl(apiEndPoint_Live)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();

        else
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(apiEndPoint_Live)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
            restAdapter = new RestAdapter.Builder().setEndpoint(apiEndPoint_Live)
                    .setRequestInterceptor(requestInterceptor).setLogLevel(RestAdapter.LogLevel.NONE)
                    .build();
        calls = restAdapter.create(ApiCalls.class);

//         calls = retrofit.create(ApiCalls.class);

    }

    @Override
    public GeneralApis getGeneralDownloader() {
        return this;
    }


    @Override
    public Observable<List<ModelJobList>> getAllJobs(int userId) {
        return calls.GetJobList(userId);
    }
}
