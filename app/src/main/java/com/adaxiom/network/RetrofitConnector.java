package com.adaxiom.network;

import com.adaxiom.criccoo.BuildConfig;
import com.adaxiom.model.request.LoginBody;
import com.adaxiom.model.request.SignUpBody;
import com.adaxiom.model.response.RM_BlockList;
import com.adaxiom.model.response.RM_CityList;
import com.adaxiom.model.response.RM_Login;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.model.response.RM_SignUp;

import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rx.Observable;

public class RetrofitConnector implements BackendConnector, BackendConnector.GeneralApis {

    private RestAdapter restAdapter;

    private String apiEndPoint_Staging, apiEndPoint_Live;
    private ApiCalls calls;
//    private Retrofit retrofit;

//    RequestInterceptor requestInterceptor = new RequestInterceptor() {
//        @Override
//        public void intercept(RequestFacade request) {
//            request.addHeader("Content-Type", "multipart/form-data");
//        }
//    };


    @Override
    public void setupConnector(String live, String staging) {
        this.apiEndPoint_Live = live;
        this.apiEndPoint_Staging = staging;

        if (BuildConfig.DEBUG)

//            restAdapter = new RestAdapter.Builder().setEndpoint(apiEndPoint_Live).
//                    setRequestInterceptor(requestInterceptor).setLogLevel(RestAdapter.LogLevel.FULL)
//                    .build();

        restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(apiEndPoint_Live)
                .setLogLevel(RestAdapter.LogLevel.FULL)
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

            restAdapter = new RestAdapter
                    .Builder()
                    .setEndpoint(apiEndPoint_Staging)
                    .setLogLevel(RestAdapter.LogLevel.NONE)
                    .build();

//            restAdapter = new RestAdapter.Builder().setEndpoint(apiEndPoint_Staging)
//                    .setRequestInterceptor(requestInterceptor).setLogLevel(RestAdapter.LogLevel.NONE)
//                    .build();
        calls = restAdapter.create(ApiCalls.class);

//         calls = retrofit.create(ApiCalls.class);

    }

    @Override
    public GeneralApis getGeneralDownloader() {
        return this;
    }


//    @Override
//    public Observable<List<ModelJobList>> getAllJobs(int userId) {
//        return calls.GetJobList(userId);
//    }

    @Override
    public Observable<RM_SignUp> signUp(SignUpBody signUpBody) {
        return calls.Sign_Up(signUpBody);
    }

    @Override
    public Observable<RM_Login> Login(LoginBody loginBody) {
        return calls.Login(loginBody);
    }

    @Override
    public Observable<RM_Login> LoginParam(String user , String pass, String from) {
        return calls.LoginParam(user, pass, from);
    }

    @Override
    public Observable<List<RM_MatchActive>> matchActive() {
        return calls.MatchActive();
    }

    @Override
    public Observable<List<RM_BlockList>> BlockList() {
        return calls.BlockList();
    }



    @Override
    public Observable<List<RM_CityList>> CityList() {
        return calls.CityList();
    }


    //    @Override
//    public Observable<RM_SignUp> signUp(String name, String uName, String email, String pswrd, String fcmToken, String city) {
//        return calls.Sign_Up(name, uName, email, pswrd, fcmToken, city);
//    }
}
