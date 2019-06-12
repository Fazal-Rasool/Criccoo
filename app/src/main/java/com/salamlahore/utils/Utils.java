package com.salamlahore.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.WindowManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils{

    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager
//                .getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) ;

        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) ;
        if (mWifi.isConnected()||mobile.isConnected()) {
            return true;
        }else{
            return false;
        }

    }


    public static void showHideLoaderView(View loaderView, boolean isShow){
        if(isShow)loaderView.setVisibility(View.VISIBLE);
        else loaderView.setVisibility(View.GONE);
    }



    public static boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }



    public static void hideSoftKeyboard(Activity context) {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
