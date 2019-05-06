package com.adaxiom.network;


import com.adaxiom.model.request.LoginBody;
import com.adaxiom.model.request.SignUpBody;
import com.adaxiom.model.response.RM_BlockList;
import com.adaxiom.model.response.RM_CityList;
import com.adaxiom.model.response.RM_Login;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.model.response.RM_SignUp;

import java.util.List;

import rx.Observable;


public interface BackendConnector {

    void setupConnector(String apiEndPointLive, String apiEndPointStaging);

    GeneralApis getGeneralDownloader();

    interface GeneralApis {
//        Observable<List<ModelJobList>> getAllJobs(int userId);

        //        Observable<RM_SignUp> signUp(String name, String uName, String email, String pswrd, String fcmToken, String city);
        Observable<RM_SignUp> signUp(SignUpBody signUpBody);
        Observable<RM_Login> Login(LoginBody loginBody);
        Observable<RM_Login> LoginParam(String user, String pass, String from);
        Observable<List<RM_MatchActive>> matchActive();
        Observable<List<RM_BlockList>> BlockList();
        Observable<List<RM_CityList>> CityList();
    }
}
