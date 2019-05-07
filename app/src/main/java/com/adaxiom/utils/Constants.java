package com.adaxiom.utils;

public interface Constants {

    //URLs
    String URL_LIVE = "http://cricoo.adaxiomdemo.com/cricoo_apis/device";
    String URL_STAGING = "http://cricoo.adaxiomdemo.com/cricoo_apis/device";

    //API Parameters
    String PARA_USER_ID= "user_id";

    //API Method Name
    String API_GET_ALL_JOBS="/warranty/{user_id}";
    String API_SIGN_UP="/signup";
    String API_LOGIN="/login";
    String API_MATCH_ACTIVE="/match_active";
    String API_BLOCK_LIST="/block_list";
    String API_CITY_LIST="/city_list";
    String API_MATCH_PREDICTION="/user_match_prediction";


    //Prefrence
    String PREF_IS_LOGIN="isLogin";
    String PREF_USER_NAME="user_name";
    String PREF_USER_EMAIL ="user_email";
    String PREF_FB_PASS="fb_pass";
    String PREF_GOOGLE_PASS ="google_pass";
    String PREF_FCM_TOKEN ="fcm_token";
}
