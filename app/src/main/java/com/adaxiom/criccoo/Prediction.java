package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_MatchPrediction;
import com.adaxiom.model.response.RM_SignUp;
import com.adaxiom.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_MATCH_ID;

public class Prediction extends AppCompatActivity {


    @BindView(R.id.tvSelectOver_SelectBlock)
    TextView tvSelectOverSelectBlock;
    @BindView(R.id.tvBallResult_1)
    TextView tvBallResult1;
    @BindView(R.id.tvBallResult_2)
    TextView tvBallResult2;
    @BindView(R.id.tvBallResult_3)
    TextView tvBallResult3;
    @BindView(R.id.tvBallResult_4)
    TextView tvBallResult4;
    @BindView(R.id.tvBallResult_5)
    TextView tvBallResult5;
    @BindView(R.id.tvBallResult_6)
    TextView tvBallResult6;
    @BindView(R.id.tvBallResult_Submit)
    TextView tvBallResultSubmit;

    private AlertDialog alertDialog;
    private Subscription getSubscription;
    public static int overId=1;

    public static void startActivity(Context context, int over) {
        overId=over;
        Intent intent = new Intent(context, Prediction.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tvBallResult_1, R.id.tvBallResult_2, R.id.tvBallResult_3,
            R.id.tvBallResult_4, R.id.tvBallResult_5, R.id.tvBallResult_6,R.id.tvBallResult_Submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBallResult_1:
                Toast.makeText(this,overId+"", Toast.LENGTH_SHORT).show();
                selectScoreDialog(tvBallResult1);
                break;
            case R.id.tvBallResult_2:
                selectScoreDialog(tvBallResult2);
                break;
            case R.id.tvBallResult_3:
                selectScoreDialog(tvBallResult3);
                break;
            case R.id.tvBallResult_4:
                selectScoreDialog(tvBallResult4);
                break;
            case R.id.tvBallResult_5:
                selectScoreDialog(tvBallResult5);
                break;
            case R.id.tvBallResult_6:
                selectScoreDialog(tvBallResult6);
                break;
            case R.id.tvBallResult_Submit:
                if(Utils.isNetworkAvailable(this))API_SubmitPrediction();
                else Toast.makeText(this, "Please connect internet first", Toast.LENGTH_SHORT).show();
                break;
        }
    }




    public void API_SubmitPrediction(){

        String opt_1 = tvBallResult1.getText().toString().trim().replace("Click","");
        String opt_2 = tvBallResult2.getText().toString().trim().replace("Click","");
        String opt_3 = tvBallResult3.getText().toString().trim().replace("Click","");
        String opt_4 = tvBallResult4.getText().toString().trim().replace("Click","");
        String opt_5 = tvBallResult5.getText().toString().trim().replace("Click","");
        String opt_6 = tvBallResult6.getText().toString().trim().replace("Click","");

        String userId = "";
        String matchId= Prefs.getString(PREF_MATCH_ID,"");
        String blockId="";
        String inning ="";
        String matchOver ="";

        if (getSubscription != null) {
            return;
        }

        getSubscription = DownloaderManager.getGeneralDownloader().API_PostMatchPredictions(userId,matchId,blockId,inning,matchOver,
                opt_1,opt_2,opt_3,opt_4,opt_5,opt_6)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_MatchPrediction>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Prediction.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final RM_MatchPrediction model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Prediction.this,model.message,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }





    public void selectScoreDialog(final TextView tvResult) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_select_score, null);
        builder.setView(dialogView);


        View tvZero = dialogView.findViewById(R.id.tvScoreAlert_0);
        View tvOne = dialogView.findViewById(R.id.tvScoreAlert_1);
        View tvTwo = dialogView.findViewById(R.id.tvScoreAlert_2);
        View tvThree = dialogView.findViewById(R.id.tvScoreAlert_3);
        View tvFour = dialogView.findViewById(R.id.tvScoreAlert_4);
        View tvFive = dialogView.findViewById(R.id.tvScoreAlert_5);
        View tvSix = dialogView.findViewById(R.id.tvScoreAlert_6);
        View tvWicket = dialogView.findViewById(R.id.tvScoreAlert_w);

        tvZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("0");
                alertDialog.dismiss();
            }
        });

        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("1");
                alertDialog.dismiss();
            }
        });

        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("2");
                alertDialog.dismiss();
            }
        });

        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("3");
                alertDialog.dismiss();
            }
        });

        tvFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("4");
                alertDialog.dismiss();
            }
        });

        tvFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("5");
                alertDialog.dismiss();
            }
        });

        tvSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("6");
                alertDialog.dismiss();
            }
        });

        tvWicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("W");
                alertDialog.dismiss();
            }
        });


        alertDialog = builder.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent_white)));
        alertDialog.show();
    }



}
