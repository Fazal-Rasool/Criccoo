package com.adaxiom.criccoo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.IsVoted;
import com.adaxiom.model.response.RM_Commentry;
import com.adaxiom.model.response.RM_GetAllVote;
import com.adaxiom.model.response.RM_WinnerPredictionNew;
import com.adaxiom.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_BLOCK_ID;
import static com.adaxiom.utils.Constants.PREF_FIRST_TEAM;
import static com.adaxiom.utils.Constants.PREF_INNING_ID;
import static com.adaxiom.utils.Constants.PREF_IS_LOGIN;
import static com.adaxiom.utils.Constants.PREF_IS_VOTE;
import static com.adaxiom.utils.Constants.PREF_MATCH_ID;
import static com.adaxiom.utils.Constants.PREF_PROFILE_IMAGE;
import static com.adaxiom.utils.Constants.PREF_SECOND_TEAM;
import static com.adaxiom.utils.Constants.PREF_TEAM_ONE_VOTE;
import static com.adaxiom.utils.Constants.PREF_TEAM_TWO_VOTE;
import static com.adaxiom.utils.Constants.PREF_USER_ID;

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
    @BindView(R.id.ivRefresh)
    ImageView ivRefresh;


    private Subscription getSubscription;
    //    String path = "https://www.youtube.com/watch?v=J9wCvyxqAxU";
    String path = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    AlertDialog alertLogout, alertVoteTeam;

    String teamOne, teamTwo, yourSelectedTeam;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        teamOne = Prefs.getString(PREF_FIRST_TEAM, "");
        teamTwo = Prefs.getString(PREF_SECOND_TEAM, "");

//        startVideo();
        setTeamLogos(teamOne, teamTwo,
                tvNameTeamOne, tvNameTeamSecond,
                ivFirstTeamDashboard, ivSecondTeamDashboard);

//        if (!checkIsVote())
//            AlertSelectTeam();

        if (Utils.isNetworkAvailable(this))
            API_IsVoted();
        else Toast.makeText(this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();

        setValues();
    }

    private void setValues() {

        String teamOneVote = Prefs.getString(PREF_TEAM_ONE_VOTE, "0");
        String teamTwoVote = Prefs.getString(PREF_TEAM_TWO_VOTE, "0");

        tvTeamVoteDashboard.setText(teamOneVote + " | " + teamTwoVote);

        setProgress(teamOneVote, teamTwoVote);

        String comp = tvTeamVoteDashboard.getText().toString().trim();
        if (comp.equalsIgnoreCase("0 | 0")) {
            API_GetAllVote();
        }


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
            R.id.viewChat_Dashboard, R.id.ivLogout, R.id.ivRefresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivPrediction_Dashboard:
                int blockId = Prefs.getInt(PREF_BLOCK_ID, 0);
                if (blockId != 0) {
                    SelectBlock.startActivity(MainActivity.this);
                } else
                    Toast.makeText(this, "Please wait until prediction will be enable", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewEarning_Dashboard:
                TotalEarning.startActivity(this);
                break;
            case R.id.viewLeaderBoard_Dashboard:
                LeaderBoard.startActivity(this);
                break;
            case R.id.viewChat_Dashboard:
                Toast.makeText(this, "Chat will available soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivLogout:
                AlertLogout();
                break;
            case R.id.ivRefresh:
                if (Utils.isNetworkAvailable(this)) {
                    API_LiveScore();
                    API_GetAllVote();
                } else
                    Toast.makeText(this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();
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
//                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
        if (run.equalsIgnoreCase("") || over.equalsIgnoreCase("")
        || over == null || run == null)
            return "";
        Double Over = Double.parseDouble(over);
        int Run = Integer.parseInt(run);
        double runrate = Run / Over;
        return (new DecimalFormat("##.#").format(runrate));
    }


    private void setTeamLogos(String teamFirst, String teamSecond,
                              TextView tvOne, TextView tvSecond,
                              ImageView ivOne, ImageView ivTwo) {

        if (!teamFirst.equalsIgnoreCase("") || !teamSecond.equalsIgnoreCase("")) {
            if (teamFirst.equalsIgnoreCase("ENGLAND")) {
                ivOne.setImageResource(R.drawable.ic_eng);
                tvOne.setText("ENG");
            } else if (teamFirst.equalsIgnoreCase("AUSTRALIA")) {
                ivOne.setImageResource(R.drawable.ic_aus);
                tvOne.setText("AUS");
            } else if (teamFirst.equalsIgnoreCase("INDIA")) {
                ivOne.setImageResource(R.drawable.ic_ind);
                tvOne.setText("IND");
            } else if (teamFirst.equalsIgnoreCase("PAKISTAN")) {
                ivOne.setImageResource(R.drawable.ic_pak);
                tvOne.setText("PAK");
            } else if (teamFirst.equalsIgnoreCase("AFGHANISTAN")) {
                ivOne.setImageResource(R.drawable.ic_afg);
                tvOne.setText("AFG");
            } else if (teamFirst.equalsIgnoreCase("BANGLADESH")) {
                ivOne.setImageResource(R.drawable.ic_ban);
                tvOne.setText("BAN");
            } else if (teamFirst.equalsIgnoreCase("NEW ZEALAND")) {
                ivOne.setImageResource(R.drawable.ic_nz);
                tvOne.setText("NZ");
            } else if (teamFirst.equalsIgnoreCase("SOUTH AFRICA")) {
                ivOne.setImageResource(R.drawable.ic_sa);
                tvOne.setText("SA");
            } else if (teamFirst.equalsIgnoreCase("SRI LANKA")) {
                ivOne.setImageResource(R.drawable.ic_sl);
                tvOne.setText("SL");
            } else if (teamFirst.equalsIgnoreCase("WEST INDIES")) {
                ivOne.setImageResource(R.drawable.ic_wi);
                tvOne.setText("WI");
            }


            if (teamSecond.equalsIgnoreCase("ENGLAND")) {
                ivTwo.setImageResource(R.drawable.ic_eng);
                tvSecond.setText("ENG");
            } else if (teamSecond.equalsIgnoreCase("AUSTRALIA")) {
                ivTwo.setImageResource(R.drawable.ic_aus);
                tvSecond.setText("AUS");
            } else if (teamSecond.equalsIgnoreCase("INDIA")) {
                ivTwo.setImageResource(R.drawable.ic_ind);
                tvSecond.setText("IND");
            } else if (teamSecond.equalsIgnoreCase("PAKISTAN")) {
                ivTwo.setImageResource(R.drawable.ic_pak);
                tvSecond.setText("PAK");
            } else if (teamSecond.equalsIgnoreCase("AFGHANISTAN")) {
                ivTwo.setImageResource(R.drawable.ic_afg);
                tvSecond.setText("AFG");
            } else if (teamSecond.equalsIgnoreCase("BANGLADESH")) {
                ivTwo.setImageResource(R.drawable.ic_ban);
                tvSecond.setText("BAN");
            } else if (teamSecond.equalsIgnoreCase("NEW ZEALAND")) {
                ivTwo.setImageResource(R.drawable.ic_nz);
                tvSecond.setText("NZ");
            } else if (teamSecond.equalsIgnoreCase("SOUTH AFRICA")) {
                ivTwo.setImageResource(R.drawable.ic_sa);
                tvSecond.setText("SA");
            } else if (teamSecond.equalsIgnoreCase("SRI LANKA")) {
                ivTwo.setImageResource(R.drawable.ic_sl);
                tvSecond.setText("SL");
            } else if (teamSecond.equalsIgnoreCase("WEST INDIES")) {
                ivTwo.setImageResource(R.drawable.ic_wi);
                tvSecond.setText("WI");
            }
        }
    }


    public void AlertLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout!");
        builder.setMessage("Do you want to logout crico?");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertLogout.dismiss();
                        Prefs.putInt(PREF_IS_LOGIN, 0);
                        Prefs.putString(PREF_PROFILE_IMAGE, "");

                        Prefs.putString(PREF_MATCH_ID, "");
                        Prefs.putString(PREF_INNING_ID, "");
                        Prefs.putInt(PREF_BLOCK_ID, 0);

                        Prefs.putString(PREF_TEAM_ONE_VOTE, "0");
                        Prefs.putString(PREF_TEAM_TWO_VOTE, "0");

                        Login.logoutFacebook();

                        Splash.startActivity(MainActivity.this);
                        finish();
                    }
                });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertLogout.dismiss();
            }
        });

        alertLogout = builder.create();
        alertLogout.show();
    }


    public void AlertSelectTeam() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Which team will win this match?");
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_alert_team_selection, null);
        dialogBuilder.setView(dialogView);

        View viewTeamOne = dialogView.findViewById(R.id.viewAlertTeamOne);
        View viewTeamTwo = dialogView.findViewById(R.id.viewAlertTeamTwo);

        ImageView ivTeamOne = dialogView.findViewById(R.id.ivAlertTeamOne);
        ImageView ivTeamTwo = dialogView.findViewById(R.id.ivAlertTeamTwo);

        final TextView tvTeamOne = dialogView.findViewById(R.id.tvNameAlertTeamOne);
        final TextView tvTeamTwo = dialogView.findViewById(R.id.tvAlertTeamTwo);

        setTeamLogos(teamOne, teamTwo,
                tvTeamOne, tvTeamTwo,
                ivTeamOne, ivTeamTwo);


        viewTeamOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shortName = tvTeamOne.getText().toString();
                yourSelectedTeam = getCompleteName(shortName);
                API_WinnerPrediction();
            }
        });

        viewTeamTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shortName = tvTeamTwo.getText().toString();
                yourSelectedTeam = getCompleteName(shortName);
                API_WinnerPrediction();
            }
        });


        alertVoteTeam = dialogBuilder.create();
        alertVoteTeam.setCancelable(false);
        alertVoteTeam.setCanceledOnTouchOutside(false);
        alertVoteTeam.show();
    }


    public void API_WinnerPrediction() {
        String matchId = Prefs.getString(PREF_MATCH_ID, "");
        int userid = Prefs.getInt(PREF_USER_ID, 0);

        avLoading.setVisibility(View.VISIBLE);

        getSubscription = DownloaderManager.getGeneralDownloader().
                API_WinnerPrediction(userid, matchId, yourSelectedTeam)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_WinnerPredictionNew>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final RM_WinnerPredictionNew model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                alertVoteTeam.dismiss();
                                newUpdateUI(model);
////                                setIsVoteFlag();
//                                if (!model.error) {
//                                    String teamOneVote = "", teamTwoVote = "";
//                                    if (compareTeamForVote(teamOne, model.team_1.prediction)) {
//                                            teamOneVote = model.team_1.total_count;
//                                            teamTwoVote = model.team_2.total_count;
//                                    } else {
//                                            teamOneVote = model.team_2.total_count;
//                                            teamTwoVote = model.team_1.total_count;
//                                    }
//                                    Prefs.putString(PREF_TEAM_ONE_VOTE, teamOneVote);
//                                    Prefs.putString(PREF_TEAM_TWO_VOTE, teamTwoVote);
//                                    setValues();
//                                } else
////                                if (!model.get(0).prediction) {
//                                    Toast.makeText(MainActivity.this, model.message, Toast.LENGTH_SHORT).show();
////                                } else
////                                    Toast.makeText(MainActivity.this, model.message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }


    public void newUpdateUI(RM_WinnerPredictionNew model) {
//        setIsVoteFlag();
        if (!model.isError()) {
            String teamOneVote = "", teamTwoVote = "";
            if (compareTeamForVote(teamOne, model.getTeam_1().getPrediction())) {
                teamOneVote = model.getTeam_1().getTotal_count() + "";
                teamTwoVote = model.getTeam_2().getTotal_count() + "";
            } else {
                teamOneVote = model.getTeam_2().getTotal_count() + "";
                teamTwoVote = model.getTeam_1().getTotal_count() + "";
            }
            Prefs.putString(PREF_TEAM_ONE_VOTE, teamOneVote);
            Prefs.putString(PREF_TEAM_TWO_VOTE, teamTwoVote);
            setValues();
        } else
//                                if (!model.get(0).prediction) {
            Toast.makeText(MainActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
//                                } else
//                                    Toast
    }


    public void API_GetAllVote() {
        String matchId = Prefs.getString(PREF_MATCH_ID, "");

        avLoading.setVisibility(View.VISIBLE);

        getSubscription = DownloaderManager.getGeneralDownloader().
                API_GetAllVote(matchId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<RM_GetAllVote>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final List<RM_GetAllVote> list) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                if (list.size() != 0 && list.size() != 1) {
                                    String teamOneVote = "", teamTwoVote = "";
                                    if (compareTeamForVote(teamOne, list.get(0).prediction)) {
                                        teamOneVote = list.get(0).total_count + "";
                                        teamTwoVote = list.get(1).total_count + "";
                                    } else {
                                        teamOneVote = list.get(1).total_count + "";
                                        teamTwoVote = list.get(0).total_count + "";
                                    }
                                    Prefs.putString(PREF_TEAM_ONE_VOTE, teamOneVote);
                                    Prefs.putString(PREF_TEAM_TWO_VOTE, teamTwoVote);
                                    setValues();
                                }
                            }
                        });
                    }
                });
    }


    public void API_IsVoted() {
        String matchId = Prefs.getString(PREF_MATCH_ID, "");
        int userid = Prefs.getInt(PREF_USER_ID, 0);

        avLoading.setVisibility(View.VISIBLE);

        getSubscription = DownloaderManager.getGeneralDownloader().
                API_IsVoted(userid, matchId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<IsVoted>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final IsVoted model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                if (model.voted != 1) {
                                    AlertSelectTeam();
                                }
                            }
                        });
                    }
                });
    }


    public boolean compareTeamForVote(String dbTeam, String apiTeam) {
        if (apiTeam.equalsIgnoreCase(dbTeam)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIsVote() {
        String str = Prefs.getString(PREF_IS_VOTE, "");
        int id = Prefs.getInt(PREF_USER_ID, 0);
        String matchId = Prefs.getString(PREF_MATCH_ID, "");
        if (!str.equalsIgnoreCase("")) {
            String[] array = str.split(",");
            String mId = array[0];
            String flag = array[1];
            String uId = array[2];
            if (mId.equalsIgnoreCase(matchId) &&
                    uId.equalsIgnoreCase(id + "") &&
                    flag.equalsIgnoreCase("1")) {
                return true;
//                if(uId.equalsIgnoreCase(id+"")){
//                    return true;
//                }else{
//                    return false;
//                }
            } else
                return false;

        } else {
            return false;
        }
    }

    public void setIsVoteFlag() {
        String matchId = Prefs.getString(PREF_MATCH_ID, "");
        int userId = Prefs.getInt(PREF_USER_ID, 0);
        String isVote = matchId + ",1," + userId;
        Prefs.putString(PREF_IS_VOTE, isVote);
    }


    public String getCompleteName(String shortName) {
        String fullName = "";
        if (shortName.equalsIgnoreCase("ENG"))
            fullName = "ENGLAND";
        else if (shortName.equalsIgnoreCase("AUS"))
            fullName = "AUSTRALIA";
        else if (shortName.equalsIgnoreCase("IND"))
            fullName = "INDIA";
        else if (shortName.equalsIgnoreCase("PAK"))
            fullName = "PAKISTAN";
        else if (shortName.equalsIgnoreCase("AFG"))
            fullName = "AFGHANISTAN";
        else if (shortName.equalsIgnoreCase("BAN"))
            fullName = "BANGLADESH";
        else if (shortName.equalsIgnoreCase("NZ"))
            fullName = "NEW ZEALAND";
        else if (shortName.equalsIgnoreCase("SA"))
            fullName = "SOUTH AFRICA";
        else if (shortName.equalsIgnoreCase("SL"))
            fullName = "SRI LANKA";
        else if (shortName.equalsIgnoreCase("WI"))
            fullName = "WEST INDIES";

        return fullName;

    }


    public void setProgress(String total1, String total2) {
        if (total1.equalsIgnoreCase("") || total2.equalsIgnoreCase(""))
            return;

        int totalOne = Integer.parseInt(total1);
        int totalTwo = Integer.parseInt(total2);

        int meanTotal = totalOne + totalTwo;

        barFirstTeamDashboard.setMax(meanTotal);
        barFirstTeamDashboard.setProgress(totalOne);

        barSecondTeamDashboard.setMax(meanTotal);
        barSecondTeamDashboard.setProgress(totalTwo);

    }


}
