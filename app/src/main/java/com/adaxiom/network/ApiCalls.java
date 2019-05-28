package com.adaxiom.network;

import com.adaxiom.model.response.RM_GetYourCash;
import com.adaxiom.model.response.IsVoted;
import com.adaxiom.model.response.RM_BlockList;
import com.adaxiom.model.response.RM_CityList;
import com.adaxiom.model.response.RM_Commentry;
import com.adaxiom.model.response.RM_GetAllVote;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.model.response.RM_Login;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.model.response.RM_MatchPrediction;
import com.adaxiom.model.response.RM_SignUp;
import com.adaxiom.model.response.RM_SignUpOther;
import com.adaxiom.model.response.RM_UserResult;
import com.adaxiom.model.response.RM_WinnerPrediction;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

import static com.adaxiom.utils.Constants.API_BLOCK_LIST;
import static com.adaxiom.utils.Constants.API_CITY_LIST;
import static com.adaxiom.utils.Constants.API_GET_COMMENTRY;
import static com.adaxiom.utils.Constants.API_GET_VOTE;
import static com.adaxiom.utils.Constants.API_GET_YOUR_CASH;
import static com.adaxiom.utils.Constants.API_LEADERBOARD;
import static com.adaxiom.utils.Constants.API_LOGIN;
import static com.adaxiom.utils.Constants.API_MATCH_ACTIVE;
import static com.adaxiom.utils.Constants.API_MATCH_PREDICTION;
import static com.adaxiom.utils.Constants.API_SIGNUP_OTHER;
import static com.adaxiom.utils.Constants.API_SIGN_UP;
import static com.adaxiom.utils.Constants.API_USER_RESULTS;
import static com.adaxiom.utils.Constants.API_USER_VOTER;
import static com.adaxiom.utils.Constants.API_WINNER_PREDICTION;

public interface ApiCalls {

//    @GET(API_GET_ALL_JOBS)
//    Observable<List<ModelJobList>> GetJobList(@Path(PARA_USER_ID) int user_id);

//    @POST(API_SIGN_UP)
//    Observable<RM_SignUp> Sign_Up(@Body SignUpBody signUpBody);

    @POST(API_LOGIN)
    Observable<RM_Login> Login(@Body Map<String, String> params);

    @FormUrlEncoded
    @POST(API_LOGIN)
    Observable<RM_Login> LoginParam(
            @Field("username") String UserName
            ,@Field("password") String password
            ,@Field("login_from") String from
            ,@Field("fcm_token") String fcmToken
    );


    @FormUrlEncoded
    @POST(API_MATCH_PREDICTION)
    Observable<RM_MatchPrediction> PostMatchPrediction(
            @Field("user_id") int userId
            ,@Field("match_id") String matchId
            ,@Field("block_id") int blockId
            ,@Field("innings") String innings
            ,@Field("match_over") int matchOver
            ,@Field("ball_1") String ball1
            ,@Field("ball_2") String ball2
            ,@Field("ball_3") String ball3
            ,@Field("ball_4") String ball4
            ,@Field("ball_5") String ball5
            ,@Field("ball_6") String ball6
    );


    @GET(API_MATCH_ACTIVE)
    Observable<List<RM_MatchActive>> MatchActive();

    @GET(API_BLOCK_LIST)
    Observable<List<RM_BlockList>> BlockList();

    @GET(API_CITY_LIST)
    Observable<List<RM_CityList>> CityList();

    @FormUrlEncoded
    @POST(API_SIGN_UP)
    Observable<RM_SignUp> Sign_Up(
            @Field("name") String name
            , @Field("username") String UserName
            , @Field("email") String email
            , @Field("password") String password
            , @Field("fcm_token") String token
            , @Field("city") String city
            , @Field("login_from") String from

    );

    @FormUrlEncoded
    @POST(API_SIGNUP_OTHER)
    Observable<RM_SignUpOther> SignUpOther(
            @Field("name") String name
            , @Field("username") String UserName
            , @Field("password") String password
            , @Field("fcm_token") String token
            , @Field("city") String city
            , @Field("login_from") String from

    );



    @GET(API_LEADERBOARD)
    Observable<RM_LeaderBoard> LeaderBoard(@Path("user_id") int userId);

    @GET(API_GET_COMMENTRY)
    Observable<RM_Commentry> Commentary(@Path("match_id") String match_id);


    @FormUrlEncoded
    @POST(API_WINNER_PREDICTION)
    Observable<RM_WinnerPrediction> WinnerPrediction(
            @Field("user_id") int userId
            ,@Field("match_id") String matchId
            ,@Field("prediction") String prediction
    );


    @GET(API_USER_RESULTS)
    Observable<List<RM_UserResult>> UserResult(@Path("user_id") int userId);



    @GET(API_USER_VOTER)
    Observable<IsVoted> IsUserVoted(@Path("user_id") int userId, @Path("match_id") String matchId);


    @GET(API_GET_VOTE)
    Observable<List<RM_GetAllVote>> GetAllVote(@Path("match_id") String matchId);


    @FormUrlEncoded
    @POST(API_GET_YOUR_CASH)
    Observable<RM_GetYourCash> GetYourCash(
            @Field("user_id") int userId
            ,@Field("phone_no") String matchId
            ,@Field("cnic") String prediction
    );


}
