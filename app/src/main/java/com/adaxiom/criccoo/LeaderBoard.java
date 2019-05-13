package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.model.response.RM_SignUp;
import com.adaxiom.utils.BaseActivity;
import com.adaxiom.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class LeaderBoard extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvLeaderBoard)
    RecyclerView rvLeaderBoard;
    @BindView(R.id.avLoading)
    LinearLayout avLoading;


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


    public void API_GetLeaderBoardData(){

        avLoading.setVisibility(View.VISIBLE);

        getSubscription = DownloaderManager.getGeneralDownloader().
                API_LeaderBoard()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<RM_LeaderBoard>>() {
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
                    public void onNext(List<RM_LeaderBoard> model) {
                        updateUi(model);
                    }
                });

    }

    public void updateUi(final List<RM_LeaderBoard> model) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                avLoading.setVisibility(View.GONE);
//                String msg = model.message;
//                Toast.makeText(Signup.this, msg, Toast.LENGTH_LONG).show();
//                if (!model.error) {
//                    MainActivity.startActivity(LeaderBoard.this);
//                    LeaderBoard.this.finish();
//                }
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
