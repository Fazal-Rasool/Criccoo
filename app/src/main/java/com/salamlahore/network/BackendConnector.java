package com.salamlahore.network;


import com.salamlahore.model.response.RM_FullCommentry;
import com.salamlahore.model.response.RM_GetEarning;
import com.salamlahore.model.response.RM_GetYourCash;
import com.salamlahore.model.response.IsVoted;
import com.salamlahore.model.response.RM_BlockList;
import com.salamlahore.model.response.RM_CityList;
import com.salamlahore.model.response.RM_Commentry;
import com.salamlahore.model.response.RM_GetAllVote;
import com.salamlahore.model.response.RM_LeaderBoard;
import com.salamlahore.model.response.RM_Login;
import com.salamlahore.model.response.RM_MatchActive;
import com.salamlahore.model.response.RM_MatchPrediction;
import com.salamlahore.model.response.RM_SignUp;
import com.salamlahore.model.response.RM_SignUpOther;
import com.salamlahore.model.response.RM_UserResult;
import com.salamlahore.model.response.RM_WinnerPredictionNew;

import java.util.List;

import rx.Observable;


public interface BackendConnector {

    void setupConnector(String apiEndPointLive, String apiEndPointStaging);

    GeneralApis getGeneralDownloader();

    interface GeneralApis {
//        Observable<List<ModelJobList>> getAllJobs(int userId);

        Observable<RM_SignUp> signUp(String name, String uName, String email,
                                     String pswrd, String fcmToken, String city, String from);

        Observable<RM_SignUpOther> signUpOther(String name, String uName, String pswrd, String fcmToken, String city, String from);

        //        Observable<RM_SignUp> signUp(SignUpBody signUpBody);
//        Observable<RM_Login> Login(LoginBody loginBody);
        Observable<RM_Login> LoginParam(String user, String pass, String from, String fcm);

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

        Observable<RM_LeaderBoard> LeaderBoard(int userId, String matchId);

        Observable<RM_Commentry> Commentary(String match_id);

        Observable<RM_WinnerPredictionNew> WinnerPrediction(int userid, String matchid, String prediction);

        Observable<List<RM_UserResult>> UserResult(int match_id);

        Observable<IsVoted> IsUserVoted(int userid, String matchId);

        Observable<List<RM_GetAllVote>> GetAllVote(String match_id);

        Observable<RM_GetYourCash> GetYourCash(int userid, String phone, String cnic);

        Observable<RM_GetEarning> GetEarning(int userid);

        Observable<List<RM_FullCommentry>> GetFullCommentry(String match_id);

    }
}
