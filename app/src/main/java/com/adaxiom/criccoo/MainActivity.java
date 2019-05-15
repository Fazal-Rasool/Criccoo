package com.adaxiom.criccoo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_Commentry;
import com.adaxiom.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_FIRST_TEAM;
import static com.adaxiom.utils.Constants.PREF_IS_LOGIN;
import static com.adaxiom.utils.Constants.PREF_MATCH_ID;
import static com.adaxiom.utils.Constants.PREF_SECOND_TEAM;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.barFirstTeam_Dashboard)
    ProgressBar barFirstTeamDashboard;
    @BindView(R.id.barSecondTeam_Dashboard)
    ProgressBar barSecondTeamDashboard;
    @BindView(R.id.tvTeamVote_Dashboard)
    TextView tvTeamVoteDashboard;
    @BindView(R.id.ivFirstTeam_Dashboard)
    ImageView ivFirstTeamDashboard;
    @BindView(R.id.ivSecondTeam_Dashboard)
    ImageView ivSecondTeamDashboard;
    @BindView(R.id.tvTotalScore_Dashboard)
    TextView tvTotalScoreDashboard;
    @BindView(R.id.tvRunRate_Dashboard)
    TextView tvRunRateDashboard;
    @BindView(R.id.tvPlayerOneScore_Dashboard)
    TextView tvPlayerOneScoreDashboard;
    @BindView(R.id.tvPlayerSecondScore_Dashboard)
    TextView tvPlayerSecondScoreDashboard;
    @BindView(R.id.ivPrediction_Dashboard)
    ImageView ivPredictionDashboard;
    @BindView(R.id.viewEarning_Dashboard)
    LinearLayout viewEarningDashboard;
    @BindView(R.id.viewLeaderBoard_Dashboard)
    LinearLayout viewLeaderBoardDashboard;
    @BindView(R.id.viewChat_Dashboard)
    LinearLayout viewChatDashboard;
    @BindView(R.id.avLoading)
    LinearLayout avLoading;
    @BindView(R.id.ivLogout)
    ImageView ivLogout;
    @BindView(R.id.tvNameTeamOne)
    TextView tvNameTeamOne;
    @BindView(R.id.tvNameTeamSecond)
    TextView tvNameTeamSecond;
    @BindView(R.id.videoViewDashboard)
    VideoView videoViewDashboard;


    private Subscription getSubscription;
    //    String path = "https://www.youtube.com/watch?v=J9wCvyxqAxU";
    String path = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    AlertDialog alert;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        startVideo();
        setTeamLogos();
    }

    private void startVideo() {


        Uri uri = Uri.parse(path);
        videoViewDashboard.setVideoURI(uri);
        videoViewDashboard.start();
//        MediaController ctlr = new MediaController(this);
//        ctlr.setMediaPlayer(videoViewDashboard);
//        videoViewDashboard.setMediaController(ctlr);
//        videoViewDashboard.requestFocus();

//        Uri uri=Uri.parse(path);
//        videoViewDashboard.setVideoPath(path);
//
//        MediaController ctlr = new MediaController(this);
//        ctlr.setMediaPlayer(videoViewDashboard);
//        videoViewDashboard.setMediaController(ctlr);
//        videoViewDashboard.requestFocus();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.isNetworkAvailable(this))
            API_LiveScore();
        else Toast.makeText(this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.ivPrediction_Dashboard, R.id.viewEarning_Dashboard, R.id.viewLeaderBoard_Dashboard,
            R.id.viewChat_Dashboard, R.id.ivLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivPrediction_Dashboard:
//                int blockId = Prefs.getInt(PREF_BLOCK_ID, 6);
//                if (blockId != 6) {
                SelectBlock.startActivity(MainActivity.this);
//                } else
//                    Toast.makeText(this, "Prediction will enable before 20 mints of match start time", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewEarning_Dashboard:
                TotalEarning.startActivity(this);
                break;
            case R.id.viewLeaderBoard_Dashboard:
                LeaderBoard.startActivity(this);
                break;
            case R.id.viewChat_Dashboard:
                Toast.makeText(this, "Under Construction!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivLogout:
                AlertLogout();
                break;
        }
    }


    public void API_LiveScore() {

        String matchId = Prefs.getString(PREF_MATCH_ID, "");

        avLoading.setVisibility(View.VISIBLE);

        getSubscription = DownloaderManager.getGeneralDownloader().
                API_Commentary(matchId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_Commentry>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(RM_Commentry model) {
                        updateUi(model);
                    }
                });
    }


    public void updateUi(final RM_Commentry model) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avLoading.setVisibility(View.GONE);
                if (!model.error) {
                    String runRate = calculateRunrate(model.total_score, model.overs);
                    tvTotalScoreDashboard.setText("Score : " + model.total_score + " in " + model.overs + " Overs");
                    tvRunRateDashboard.setText("R/R : " + runRate + " per over");
                    tvPlayerOneScoreDashboard.setText(model.player_1);
                    tvPlayerSecondScoreDashboard.setText(model.player_2);
                }
            }
        });

    }

    public String calculateRunrate(String run, String over) {
        if (run.equalsIgnoreCase("") || over.equalsIgnoreCase(""))
            return "";
        Double Over = Double.parseDouble(over);
        int Run = Integer.parseInt(run);
        double runrate = Run / Over;
        return (new DecimalFormat("##.#").format(runrate));
    }


    private void setTeamLogos() {
        String teamFirst = Prefs.getString(PREF_FIRST_TEAM, "");
        String teamSecond = Prefs.getString(PREF_SECOND_TEAM, "");

        if (!teamFirst.equalsIgnoreCase("") || !teamSecond.equalsIgnoreCase("")) {
            if (teamFirst.equalsIgnoreCase("ENGLAND")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_eng);
                tvNameTeamOne.setText("ENG");
            } else if (teamFirst.equalsIgnoreCase("AUSTRALIA")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_aus);
                tvNameTeamOne.setText("AUS");
            } else if (teamFirst.equalsIgnoreCase("INDIA")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_ind);
                tvNameTeamOne.setText("IND");
            } else if (teamFirst.equalsIgnoreCase("PAKISTAN")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_pak);
                tvNameTeamOne.setText("PAK");
            } else if (teamFirst.equalsIgnoreCase("AFGHANISTAN")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_afg);
                tvNameTeamOne.setText("AFG");
            } else if (teamFirst.equalsIgnoreCase("BANGLADESH")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_ban);
                tvNameTeamOne.setText("BAN");
            } else if (teamFirst.equalsIgnoreCase("NEW ZEALAND")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_nz);
                tvNameTeamOne.setText("NZ");
            } else if (teamFirst.equalsIgnoreCase("SOUTH AFRICA")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_sa);
                tvNameTeamOne.setText("SA");
            } else if (teamFirst.equalsIgnoreCase("SRI LANKA")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_sl);
                tvNameTeamOne.setText("SL");
            } else if (teamFirst.equalsIgnoreCase("WEST INDIES")) {
                ivFirstTeamDashboard.setImageResource(R.drawable.ic_wi);
                tvNameTeamOne.setText("WI");
            }


            if (teamSecond.equalsIgnoreCase("ENGLAND")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_eng);
                tvNameTeamSecond.setText("ENG");
            } else if (teamSecond.equalsIgnoreCase("AUSTRALIA")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_aus);
                tvNameTeamSecond.setText("AUS");
            } else if (teamSecond.equalsIgnoreCase("INDIA")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_ind);
                tvNameTeamSecond.setText("IND");
            } else if (teamSecond.equalsIgnoreCase("PAKISTAN")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_pak);
                tvNameTeamSecond.setText("PAK");
            } else if (teamSecond.equalsIgnoreCase("AFGHANISTAN")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_afg);
                tvNameTeamSecond.setText("AFG");
            } else if (teamSecond.equalsIgnoreCase("BANGLADESH")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_ban);
                tvNameTeamSecond.setText("BAN");
            } else if (teamSecond.equalsIgnoreCase("NEW ZEALAND")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_nz);
                tvNameTeamSecond.setText("NZ");
            } else if (teamSecond.equalsIgnoreCase("SOUTH AFRICA")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_sa);
                tvNameTeamSecond.setText("SA");
            } else if (teamSecond.equalsIgnoreCase("SRI LANKA")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_sl);
                tvNameTeamSecond.setText("SL");
            } else if (teamSecond.equalsIgnoreCase("WEST INDIES")) {
                ivSecondTeamDashboard.setImageResource(R.drawable.ic_wi);
                tvNameTeamSecond.setText("WI");
            }
        }
    }


    public void AlertLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout!");
        builder.setMessage("Do you want to logout criccoo?");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alert.dismiss();
                        Prefs.putInt(PREF_IS_LOGIN, 0);
                        Login.startActivity(MainActivity.this);
                        finish();
                    }
                });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alert.dismiss();
            }
        });

        alert = builder.create();
        alert.show();
    }


}
