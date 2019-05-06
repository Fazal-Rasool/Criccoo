package com.adaxiom.network;

import com.adaxiom.model.request.LoginBody;
import com.adaxiom.model.request.SignUpBody;
import com.adaxiom.model.response.RM_BlockList;
import com.adaxiom.model.response.RM_CityList;
import com.adaxiom.model.response.RM_Login;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.model.response.RM_SignUp;

import java.util.List;

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
import static com.adaxiom.utils.Constants.API_LOGIN;
import static com.adaxiom.utils.Constants.API_MATCH_ACTIVE;
import static com.adaxiom.utils.Constants.API_SIGN_UP;
import static com.adaxiom.utils.Constants.PARA_USER_ID;

public interface ApiCalls {

//    @GET(API_GET_ALL_JOBS)
//    Observable<List<ModelJobList>> GetJobList(@Path(PARA_USER_ID) int user_id);

    @POST(API_SIGN_UP)
    Observable<RM_SignUp> Sign_Up(@Body SignUpBody signUpBody);

    @POST(API_LOGIN)
    Observable<RM_Login> Login(@Body LoginBody loginBody);

    @FormUrlEncoded
    @POST(API_LOGIN)
    Observable<RM_Login> LoginParam(
            @Field("username") String UserName
            ,@Field("password") String password
            ,@Field("login_from") String from

    );


    @GET(API_MATCH_ACTIVE)
    Observable<List<RM_MatchActive>> MatchActive();

    @GET(API_BLOCK_LIST)
    Observable<List<RM_BlockList>> BlockList();

    @GET(API_CITY_LIST)
    Observable<List<RM_CityList>> CityList();

//    @FormUrlEncoded
//    @POST(API_SIGN_UP)
//    Observable<RM_SignUp> Sign_Up(
//            @Field("name") String name
//            , @Field("username") String UserName
//            , @Field("email") String email
//            , @Field("password") String password
//            , @Field("fcm_token") String token
//            , @Field("city") String city
//
//    );


}
