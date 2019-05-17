package com.adaxiom.network;

import com.adaxiom.model.response.RM_BlockList;
import com.adaxiom.model.response.RM_CityList;
import com.adaxiom.model.response.RM_Commentry;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.model.response.RM_Login;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.model.response.RM_MatchPrediction;
import com.adaxiom.model.response.RM_SignUp;
import com.adaxiom.model.response.RM_WinnerPrediction;

import java.util.List;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

import static com.adaxiom.utils.Constants.API_BLOCK_LIST;
import static com.adaxiom.utils.Constants.API_CITY_LIST;
import static com.adaxiom.utils.Constants.API_GET_ALL_JOBS;
import static com.adaxiom.utils.Constants.API_GET_COMMENTRY;
import static com.adaxiom.utils.Constants.API_LEADERBOARD;
import static com.adaxiom.utils.Constants.API_LOGIN;
import static com.adaxiom.utils.Constants.API_MATCH_ACTIVE;
import static com.adaxiom.utils.Constants.API_MATCH_PREDICTION;
import static com.adaxiom.utils.Constants.API_SIGN_UP;
import static com.adaxiom.utils.Constants.API_WINNER_PREDICTION;
import static com.adaxiom.utils.Constants.PARA_USER_ID;

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


}
