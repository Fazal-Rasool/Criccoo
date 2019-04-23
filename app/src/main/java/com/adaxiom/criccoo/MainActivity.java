package com.adaxiom.criccoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.ModelJobList;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Subscription getJobsListSubscription;
    TextView tvShowResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShowResponse = (TextView) findViewById(R.id.tvResponse);

        callAPI();
    }

    public void callAPI(){

        if (getJobsListSubscription != null) {
            return;
        }

        getJobsListSubscription = DownloaderManager.getGeneralDownloader().getJobs(29)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<ModelJobList>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(List<ModelJobList> modelJobList) {

                        updateUi(modelJobList);
                    }
                });

    }

    public void updateUi(final List<ModelJobList> modelJobList){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvShowResponse.setText(modelJobList.get(0).imgBw);
            }
        });
    }

}
