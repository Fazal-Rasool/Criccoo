package com.salamlahore.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.salamlahore.adapter.AdapterCommentry;
import com.salamlahore.manager.DownloaderManager;
import com.salamlahore.model.response.RM_FullCommentry;
import com.salamlahore.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.salamlahore.utils.Constants.PREF_MATCH_ID;

public class Commentry extends AppCompatActivity {

    @BindView(R.id.recyclerCommentry)
    RecyclerView recyclerCommentry;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;


    AdapterCommentry reAdapter;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, Commentry.class);
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentry);
        ButterKnife.bind(this);


        setListener();


    }


    @Override
    public void onResume() {
        super.onResume();
        if (Utils.isNetworkAvailable(Commentry.this))
            API_FullCommentry();
        else
            Toast.makeText(Commentry.this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();
    }


    private void setListener() {

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.isNetworkAvailable(Commentry.this))
                    API_FullCommentry();
                else
                    Toast.makeText(Commentry.this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }



    public void setAdapter(List<RM_FullCommentry> list) {

        recyclerCommentry.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(Commentry.this);
        recyclerCommentry.setLayoutManager(layoutManager);


        reAdapter = new AdapterCommentry(Commentry.this, list,
                new AdapterCommentry.RecyclerViewItemClickListener() {
                    @Override
                    public void recyclerViewListClicked(View v, int position) {


                    }
                });

        recyclerCommentry.setAdapter(reAdapter);
    }




    public void API_FullCommentry() {
        String matchId = Prefs.getString(PREF_MATCH_ID, "");

        swipeContainer.setRefreshing(true);

        DownloaderManager.getGeneralDownloader().
                API_FullCommentry(matchId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<RM_FullCommentry>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeContainer.setRefreshing(false);
                                Toast.makeText(Commentry.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final List<RM_FullCommentry> list) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeContainer.setRefreshing(false);
                                setAdapter(list);
                            }
                        });
                    }
                });
    }

}
