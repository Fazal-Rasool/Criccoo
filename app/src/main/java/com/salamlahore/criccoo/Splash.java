package com.salamlahore.criccoo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.salamlahore.manager.DownloaderManager;
import com.salamlahore.model.response.RM_MatchActive;
import com.salamlahore.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixplicity.easyprefs.library.Prefs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.salamlahore.utils.Constants.PREF_FCM_TOKEN;
import static com.salamlahore.utils.Constants.PREF_FIRST_TEAM;
import static com.salamlahore.utils.Constants.PREF_IS_LOGIN;
import static com.salamlahore.utils.Constants.PREF_MATCH_ID;
import static com.salamlahore.utils.Constants.PREF_SECOND_TEAM;
import static com.salamlahore.utils.Constants.PREF_START_TIME;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    private Subscription getSubscription;

    AlertDialog alertLogout;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, Splash.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


//        generateKeyHash();


        if(Utils.isNetworkAvailable(this)) {
            getFcmToken();
            GetMatchId();
        }
        else {
            AlertInternet();
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

            }
        });

    }



    public void GetMatchId(){
//        if (getSubscription != null) {
//            return;
//        }

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
                                    Prefs.putString(PREF_START_TIME,model.get(0).time);
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



    public void AlertInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Please connect to internet");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertLogout.dismiss();
                        finish();
                    }
                });

//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                alertLogout.dismiss();
//            }
//        });

        alertLogout = builder.create();
        alertLogout.show();
    }





    public void generateKeyHash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.salamlahore.criccoo",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

}
