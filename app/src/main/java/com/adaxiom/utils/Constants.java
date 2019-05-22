package com.adaxiom.utils;

public interface Constants {

    //URLs
    String URL_LIVE = "http://cricoo.adaxiomdemo.com/cricoo_apis/device";
    String URL_STAGING = "http://cricoo.adaxiomdemo.com/cricoo_apis/device";

//    String URL_LIVE = "http://192.168.0.109/cricoo/cricoo_apis/device";
//    String URL_STAGING = "http://192.168.0.109/cricoo/cricoo_apis/device";

    //API Parameters
    String PARA_USER_ID= "user_id";


    //API Method Name
    String API_GET_ALL_JOBS="/warranty/{user_id}";
    String API_SIGN_UP="/signup";
    String API_SIGNUP_OTHER="/signup_other";
    String API_LOGIN="/login";
    String API_MATCH_ACTIVE="/match_active";
    String API_BLOCK_LIST="/block_list";
    String API_CITY_LIST="/city_list";
    String API_MATCH_PREDICTION="/user_match_prediction";
    String API_GET_COMMENTRY="/get_full_commentry/{match_id}";
    String API_LEADERBOARD="/leaderboards/{user_id}";
    String API_WINNER_PREDICTION="/winner_prediction";


    //Prefrence
    String PREF_IS_LOGIN="isLogin";
    String PREF_USER_ID="user_id";
    String PREF_USER_NAME="user_name";
    String PREF_USER_EMAIL ="user_email";
    String PREF_FB_PASS="fb_pass";
    String PREF_GOOGLE_PASS ="google_pass";
    String PREF_FCM_TOKEN ="fcm_token";
    String PREF_MATCH_ID ="match_id";
    String PREF_BLOCK_ID ="block_id";
    String PREF_INNING_ID ="inning_id";
    String PREF_FIRST_TEAM ="team_one_id";
    String PREF_SECOND_TEAM="team_two_id";
    String PREF_IS_VOTE="is_vote";
    String PREF_TEAM_ONE_VOTE="team_one_vote";
    String PREF_TEAM_TWO_VOTE="team_two_vote";

}
