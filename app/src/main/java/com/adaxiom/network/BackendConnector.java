package com.adaxiom.network;


import com.adaxiom.model.request.SignUpBody;
import com.adaxiom.model.response.ModelJobList;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.model.response.RM_SignUp;

import java.util.List;

import rx.Observable;


public interface BackendConnector {

    void setupConnector(String apiEndPointLive, String apiEndPointStaging);

    GeneralApis getGeneralDownloader();

    interface GeneralApis {
        Observable<List<ModelJobList>> getAllJobs(int userId);

        //        Observable<RM_SignUp> signUp(String name, String uName, String email, String pswrd, String fcmToken, String city);
        Observable<RM_SignUp> signUp(SignUpBody signUpBody);
        Observable<List<RM_MatchActive>> matchActive();
    }
}
