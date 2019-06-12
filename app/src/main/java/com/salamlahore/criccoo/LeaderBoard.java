package com.salamlahore.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.salamlahore.adapter.AdapterLeaderBoard;
import com.salamlahore.manager.DownloaderManager;
import com.salamlahore.model.response.RM_LeaderBoard;
import com.salamlahore.utils.BaseActivity;
import com.salamlahore.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.salamlahore.utils.Constants.PREF_MATCH_ID;
import static com.salamlahore.utils.Constants.PREF_USER_ID;

public class LeaderBoard extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvLeaderBoard)
    RecyclerView rvLeaderBoard;
    @BindView(R.id.avLoading)
    LinearLayout avLoading;
    @BindView(R.id.tvMyRank)
    TextView tvMyRank;
    @BindView(R.id.tvTotalpoints)
    TextView tvTotalpoints;
    @BindView(R.id.tvHighestPoints)
    TextView tvHighestPoints;
    @BindView(R.id.ivMyProfile)
    ImageView ivMyProfile;


    public static AdapterLeaderBoard reAdapter;
    private Subscription getSubscription;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LeaderBoard.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_leader_board);
        ButterKnife.bind(this);


        setToolbar(toolbar, "Leader Board");

        if (Utils.isNetworkAvailable(this))
            API_GetLeaderBoardData();
        else Toast.makeText(this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();


    }


    public void setAdapter(RM_LeaderBoard mList) {

        if (mList.user.size() != 0) {
            String myRank = mList.user.get(0).rank;
            String total = mList.user.get(0).total_score;
            tvMyRank.setText(myRank);
            if (total != null)
                tvTotalpoints.setText(total);
            else tvTotalpoints.setText("0.0");
        }
        if (mList.all_users.size() != 0) {
            String highestScore = mList.all_users.get(0).total_score;
            tvHighestPoints.setText(highestScore);
        }


        rvLeaderBoard.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvLeaderBoard.setLayoutManager(layoutManager);


        reAdapter = new AdapterLeaderBoard(this, mList,
                new AdapterLeaderBoard.RecyclerViewItemClickListener() {
                    @Override
                    public void recyclerViewListClicked(View v, int position) {

                    }
                });

        rvLeaderBoard.setAdapter(reAdapter);
    }

    public void API_GetLeaderBoardData() {

        int user_id = Prefs.getInt(PREF_USER_ID, 0);
        String matchid = Prefs.getString(PREF_MATCH_ID, "");

        avLoading.setVisibility(View.VISIBLE);

        getSubscription = DownloaderManager.getGeneralDownloader().
                API_LeaderBoard(user_id, matchid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_LeaderBoard>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(LeaderBoard.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(RM_LeaderBoard list) {
                        updateUi(list);
                    }
                });

    }

    public void updateUi(final RM_LeaderBoard mList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avLoading.setVisibility(View.GONE);
                setAdapter(mList);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.ivMyProfile)
    public void onViewClicked() {
        String points = tvTotalpoints.getText().toString();
        MyProfile.startActivity(this, points);
    }
}
