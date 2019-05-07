package com.adaxiom.criccoo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixplicity.easyprefs.library.Prefs;

import static com.adaxiom.utils.Constants.PREF_FCM_TOKEN;
import static com.adaxiom.utils.Constants.PREF_IS_LOGIN;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getFcmToken();

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
            MainActivity.startActivity(Splash.this);
//            Login.startActivity(Splash.this);
            finish();
        }else{
            MainActivity.startActivity(Splash.this);
//            Login.startActivity(Splash.this);
            finish();
        }

    }


    public void getFcmToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Splash.this,
                new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

                String newToken = instanceIdResult.getToken();
                Log.d("NEW_TOKEN_SPLASH", newToken);
                Prefs.putString(PREF_FCM_TOKEN, newToken);
            }
        });

    }
}
