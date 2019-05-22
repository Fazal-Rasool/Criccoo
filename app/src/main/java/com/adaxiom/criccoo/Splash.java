package com.adaxiom.criccoo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_Login;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.utils.Utils;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixplicity.easyprefs.library.Prefs;

import io.fabric.sdk.android.Fabric;
import java.util.List;

import io.fabric.sdk.android.services.common.Crash;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_FCM_TOKEN;
import static com.adaxiom.utils.Constants.PREF_FIRST_TEAM;
import static com.adaxiom.utils.Constants.PREF_IS_LOGIN;
import static com.adaxiom.utils.Constants.PREF_MATCH_ID;
import static com.adaxiom.utils.Constants.PREF_SECOND_TEAM;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    private Subscription getSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if(Utils.isNetworkAvailable(this)) {
            getFcmToken();
        }
        else {
            Toast.makeText(this,R.string.internet_connectivity_msg,Toast.LENGTH_SHORT).show();
            this.finish();
        }

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                callNewActivity();
//            }
//        }, SPLASH_TIME_OUT);


    }


    public void callNewActivity(){

        int isLogIn = Prefs.getInt(PREF_IS_LOGIN, 0);

        if(isLogIn == 1){
            MainActivity.startActivity(Splash.this);
//            Login.startActivity(Splash.this);
            finish();
        }else{
//            MainActivity.startActivity(Splash.this);
            Login.startActivity(Splash.this);
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
                GetMatchId();
            }
        });

    }



    public void GetMatchId(){
        if (getSubscription != null) {
            return;
        }

        getSubscription = DownloaderManager.getGeneralDownloader().API_MatchActive()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<RM_MatchActive>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Utils.showHideLoaderView(avLoading,false);
                                Toast.makeText(Splash.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final List<RM_MatchActive> model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (model.get(0).error != true) {
                                    Prefs.putString(PREF_MATCH_ID,model.get(0).match_id);
                                    Prefs.putString(PREF_FIRST_TEAM,model.get(0).team_1);
                                    Prefs.putString(PREF_SECOND_TEAM,model.get(0).team_2);
                                    callNewActivity();
                                } else
                                    Toast.makeText(Splash.this, "Error while getting match Id!!!",
                                            Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
    }
}
