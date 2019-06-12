package com.salamlahore.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.salamlahore.manager.DownloaderManager;
import com.salamlahore.model.response.RM_MatchPrediction;
import com.salamlahore.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.salamlahore.utils.Constants.PREF_BLOCK_ID;
import static com.salamlahore.utils.Constants.PREF_INNING_ID;
import static com.salamlahore.utils.Constants.PREF_MATCH_ID;
import static com.salamlahore.utils.Constants.PREF_USER_ID;

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
    @BindView(R.id.avLoading)
    LinearLayout avLoading;

    private AlertDialog alertDialog;
    private Subscription getSubscription;
    public static int overId = 0;
    public static int blockId = 0;
    String opt_1, opt_2, opt_3, opt_4, opt_5, opt_6;

    public static void startActivity(Context context, int over) {
        overId = over;
        Intent intent = new Intent(context, Prediction.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prediction);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.tvBallResult_1, R.id.tvBallResult_2, R.id.tvBallResult_3,
            R.id.tvBallResult_4, R.id.tvBallResult_5, R.id.tvBallResult_6, R.id.tvBallResult_Submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBallResult_1:
//                Toast.makeText(this, overId + "", Toast.LENGTH_SHORT).show();
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
                if (Utils.isNetworkAvailable(this)) {
                    opt_1 = tvBallResult1.getText().toString().trim().replace("Click", "");
                    opt_2 = tvBallResult2.getText().toString().trim().replace("Click", "");
                    opt_3 = tvBallResult3.getText().toString().trim().replace("Click", "");
                    opt_4 = tvBallResult4.getText().toString().trim().replace("Click", "");
                    opt_5 = tvBallResult5.getText().toString().trim().replace("Click", "");
                    opt_6 = tvBallResult6.getText().toString().trim().replace("Click", "");

                    if (opt_1.equalsIgnoreCase("") ||
                            opt_2.equalsIgnoreCase("") ||
                            opt_3.equalsIgnoreCase("") ||
                            opt_4.equalsIgnoreCase("") ||
                            opt_5.equalsIgnoreCase("") ||
                            opt_6.equalsIgnoreCase("")
                    ) {
                        Toast.makeText(this, "Predict againset all balls first", Toast.LENGTH_SHORT).show();
                    } else API_SubmitPrediction();
                } else
                    Toast.makeText(this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void API_SubmitPrediction() {

        avLoading.setVisibility(View.VISIBLE);

        int userId = Prefs.getInt(PREF_USER_ID,0);
        String matchId = Prefs.getString(PREF_MATCH_ID, "");
        String inning = Prefs.getString(PREF_INNING_ID,"");
        blockId = Prefs.getInt(PREF_BLOCK_ID,0);

        if (getSubscription != null) {
            return;
        }

        getSubscription = DownloaderManager.getGeneralDownloader().API_PostMatchPredictions(
                userId,
                matchId,
                blockId,
                inning,
                overId,
                opt_1, opt_2, opt_3, opt_4, opt_5, opt_6
        )
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
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(Prediction.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final RM_MatchPrediction model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(Prediction.this, model.message, Toast.LENGTH_SHORT).show();
                                Prediction.this.finish();
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
