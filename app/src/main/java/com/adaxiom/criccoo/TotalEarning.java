package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_USER_ID;

public class TotalEarning extends AppCompatActivity {

    @BindView(R.id.tvTotalCash)
    TextView tvTotalCash;
    @BindView(R.id.ivGetYourCash)
    View ivGetYourCash;
    @BindView(R.id.avLoading)
    LinearLayout avLoading;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TotalEarning.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_earning);
        ButterKnife.bind(this);

        if (Utils.isNetworkAvailable(this))
            API_GetTotalEarning();
        else Toast.makeText(this,R.string.internet_connectivity_msg,Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.ivGetYourCash)
    public void onViewClicked() {
        Toast.makeText(this,"Wait, we are fetching your data",Toast.LENGTH_SHORT).show();
    }



    public void API_GetTotalEarning() {

        int user_id = Prefs.getInt(PREF_USER_ID, 0);

        avLoading.setVisibility(View.VISIBLE);

         DownloaderManager.getGeneralDownloader().
                API_LeaderBoard(user_id)
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
                                Toast.makeText(TotalEarning.this, e.toString(), Toast.LENGTH_LONG).show();
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
                if (mList.user.size() != 0) {
                    String total_score = mList.user.get(0).total_score;
                    if(total_score != null || !total_score.equalsIgnoreCase(""))
                    tvTotalCash.setText(total_score+"/-");
                    else tvTotalCash.setText("0.0/-");
                }

            }
        });
    }





}
