package com.adaxiom.network;


import com.adaxiom.model.response.RM_BlockList;
import com.adaxiom.model.response.RM_CityList;
import com.adaxiom.model.response.RM_Login;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.model.response.RM_MatchPrediction;
import com.adaxiom.model.response.RM_SignUp;

import java.util.List;

import retrofit.http.Field;
import rx.Observable;


public interface BackendConnector {

    void setupConnector(String apiEndPointLive, String apiEndPointStaging);

    GeneralApis getGeneralDownloader();

    interface GeneralApis {
//        Observable<List<ModelJobList>> getAllJobs(int userId);

        Observable<RM_SignUp> signUp(String name, String uName, String email, String pswrd, String fcmToken, String city);

        //        Observable<RM_SignUp> signUp(SignUpBody signUpBody);
//        Observable<RM_Login> Login(LoginBody loginBody);
        Observable<RM_Login> LoginParam(String user, String pass, String from);

        Observable<List<RM_MatchActive>> matchActive();

        Observable<List<RM_BlockList>> BlockList();

        Observable<List<RM_CityList>> CityList();

        Observable<RM_MatchPrediction> PostMatchPrediction(
                int userId,
                String matchId,
                int blockId,
                String innings,
                int matchOver,
                String ball_1,
                String ball_2,
                String ball_3,
                String ball_4,
                String ball_5,
                String ball_6
        );

    }
}
