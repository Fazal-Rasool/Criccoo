package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adaxiom.adapter.AdapterLeaderBoard;
import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.model.response.RM_SignUp;
import com.adaxiom.utils.BaseActivity;
import com.adaxiom.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_USER_ID;

public class LeaderBoard extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvLeaderBoard)
    RecyclerView rvLeaderBoard;
    @BindView(R.id.avLoading)
    LinearLayout avLoading;

    public static AdapterLeaderBoard reAdapter;


    private Subscription getSubscription;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LeaderBoard.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        ButterKnife.bind(this);


        setToolbar(toolbar, "Leader Board");

        if(Utils.isNetworkAvailable(this))
            API_GetLeaderBoardData();
        else Toast.makeText(this,"Please connect to internet first", Toast.LENGTH_SHORT).show();


    }




    public void setAdapter(RM_LeaderBoard mList){
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

    public void API_GetLeaderBoardData(){

        int user_id = Prefs.getInt(PREF_USER_ID,0);

        avLoading.setVisibility(View.VISIBLE);

        getSubscription = DownloaderManager.getGeneralDownloader().
                API_LeaderBoard(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_LeaderBoard>() {
                    @Override
                    public void onCompleted() { }

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


}
