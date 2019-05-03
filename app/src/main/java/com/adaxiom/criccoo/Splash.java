package com.adaxiom.criccoo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pixplicity.easyprefs.library.Prefs;

import static com.adaxiom.utils.Constants.PREF_IS_LOGIN;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callNewActivity();
            }
        }, SPLASH_TIME_OUT);


    }


    public void callNewActivity(){

        int isLogIn = Prefs.getInt(PREF_IS_LOGIN, 0);

        if(isLogIn == 1){
            Login.startLoginActivity(Splash.this);
            finish();
        }else{
//            MainActivity.startMainActivity(Splash.this);
            Login.startLoginActivity(Splash.this);
            finish();
        }

    }
}
