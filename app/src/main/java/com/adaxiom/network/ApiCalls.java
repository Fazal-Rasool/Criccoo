package com.adaxiom.network;

import com.adaxiom.model.ModelJobList;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

import static com.adaxiom.utils.Constants.API_GET_ALL_JOBS;
import static com.adaxiom.utils.Constants.PARA_USER_ID;

public interface ApiCalls {

    @GET(API_GET_ALL_JOBS)
    Observable<List<ModelJobList>> GetJobList(@Path(PARA_USER_ID) int user_id);
}
