package com.adaxiom.network;

import com.adaxiom.model.request.SignUpBody;
import com.adaxiom.model.response.ModelJobList;
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

import static com.adaxiom.utils.Constants.API_GET_ALL_JOBS;
import static com.adaxiom.utils.Constants.API_MATCH_ACTIVE;
import static com.adaxiom.utils.Constants.API_SIGN_UP;
import static com.adaxiom.utils.Constants.PARA_USER_ID;

public interface ApiCalls {

    @GET(API_GET_ALL_JOBS)
    Observable<List<ModelJobList>> GetJobList(@Path(PARA_USER_ID) int user_id);

    @POST(API_SIGN_UP)
    Observable<RM_SignUp> Sign_Up(@Body SignUpBody signUpBody);

    @GET(API_MATCH_ACTIVE)
    Observable<List<RM_MatchActive>> MatchActive();

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
