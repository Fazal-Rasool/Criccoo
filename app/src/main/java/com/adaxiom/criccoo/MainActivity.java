package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_Commentry;
import com.adaxiom.model.response.RM_LeaderBoard;
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
import static com.adaxiom.utils.Constants.PREF_MATCH_ID;

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


    private Subscription getSubscription;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.isNetworkAvailable(this))
            API_LiveScore();
        else Toast.makeText(this, "Please connect to internet first", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.ivPrediction_Dashboard, R.id.viewEarning_Dashboard, R.id.viewLeaderBoard_Dashboard, R.id.viewChat_Dashboard})
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
                Toast.makeText(this, "Under Construction!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewLeaderBoard_Dashboard:
                LeaderBoard.startActivity(this);
                break;
            case R.id.viewChat_Dashboard:
                Toast.makeText(this, "Under Construction!!!", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void API_LiveScore() {

        String matchId = Prefs.getString(PREF_MATCH_ID,"");

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


    public void updateUi(final RM_Commentry model){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avLoading.setVisibility(View.GONE);
                 if(!model.error){
                     String runRate = calculateRunrate(model.total_score,model.overs);
                     tvTotalScoreDashboard.setText("Score : "+model.total_score+" in "+model.overs+" Overs");
                     tvRunRateDashboard.setText("R/R : "+runRate+" per over");
                     tvPlayerOneScoreDashboard.setText(model.player_1);
                     tvPlayerSecondScoreDashboard.setText(model.player_2);
                 }
            }
        });

    }

    public String calculateRunrate(String run, String over){
        if(run.equalsIgnoreCase("") || over.equalsIgnoreCase(""))
            return "";
        Double Over = Double.parseDouble(over);
        int Run = Integer.parseInt(run);
        double runrate = Run/Over;
        return (new DecimalFormat("##.#").format(runrate));
    }
}
